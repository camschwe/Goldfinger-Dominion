package Game;

import Client_Server.Chat.Message;
import Client_Server.Client.Client;
import Client_Server.GameObject;
import End.EndController;
import End.EndModel;
import Localisation.Localisator;
import Login.EndView;
import Login.LoginController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import sun.applet.Main;
import sun.rmi.runtime.Log;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;

/**
 * Created by camillo.schweizer on 21.10.2017.
 *
 * Hauptkontroller zur Steuerung der GameView mit Methoden zum GUI Update. Zudem handelt der Controller die Spielzüge
 * des Gegners.
 */


public class GameController {

    /**
     * Hauptkontroller zur Steuerung der GameView mit Methoden zum GUI Update
     */

    private GameView gameView;
    private Localisator localisator;
    private GameModel gameModel;
    private HandCardController handCardController;
    private FieldCardController fieldCardController;
    private static Client client;
    private EndView endView;
    private EndController endController;
    private EndModel endModel;



    public GameController(GameView gameView, Localisator localisator, GameModel gameModel, Client client){
        this.gameView = gameView;
        this.localisator = localisator;
        this.gameModel = gameModel;
        this.client = client;

        /**
         * Aktualisierung der Label nach dem Spielstart
         */

        gameView.pointLabel1.setText(localisator.getResourceBundle().getString("point")+ ":\t" + gameModel.getPlayer().getPoints());
        gameView.playerLabel1.setText(client.getClientName());


        /**
         * Gegnerische Labels sowie Karten werden ausgeblendet, wenn nur 1 Spieler vorhanden ist.
         */

        if(gameModel.getPlayerList().size()== 1){
            gameView.player2Box.setVisible(false);
            gameView.playerLabel2.getStyleClass().add("invisible");
            gameView.putStapelPlayer2.getStyleClass().add("invisible");
            gameView.drawStapelPlayer2.getStyleClass().add("invisible");
            gameView.pointLabel2.getStyleClass().add("invisible");
            gameView.phaseLabel2.getStyleClass().add("invisible");
            gameView.moneyLabel2.getStyleClass().add("invisible");
        }

        /**
         * Initialisierung des Controllers für die Feldkarten
         */

        fieldCardController = new FieldCardController(gameView, localisator, gameModel, this);

        /**
         * Musik Button Design bei Start des Controllers festlegen
         */

        if (client.getMusicActivated()) {
            Platform.runLater(() -> gameView.musicButton.getStyleClass().clear());
            Platform.runLater(() -> gameView.musicButton.getStyleClass().add("musicButtonOn"));
        } else {
            Platform.runLater(() -> gameView.musicButton.getStyleClass().clear());
            Platform.runLater(() -> gameView.musicButton.getStyleClass().add("musicButtonOff"));
        }

        gameView.gameStage.setOnCloseRequest(event -> {
            client.stopClient();
            Platform.exit();
            System.exit(0);
        });

        /**
         * Eventhändler für den Button um eine Phase zu überspringen. Führ die Notwendigen Updates beim Spiel sowie auch
         * dem GUI durch.
         */

        gameView.phaseButton.setOnAction(event -> {
            if(gameModel.getPlayer().isYourTurn()) {
                gameModel.getPlayer().endPhase();

                Platform.runLater(() -> {
                    handCardController.updateHandCardsView();
                    player1LabelUpdate();
                    fieldCardController.fieldCardsGlowingUpdate(fieldCardController.getResourceButtons());
                    fieldCardController.fieldCardsGlowingUpdate(fieldCardController.getActionButtons());
                    });
            }
            if (gameModel.getPlayer().isTurnEnded()){
                getClient().sendObject(new Message(6, gameModel.getPlayer().getPlayerName(), "Turn ended"));
                gameModel.getPlayer().setTurnEnded(false);
            }

        });

        /**
         * Eventhändler für den Chattbutton. Mit dem im Textfeld vorhandenen String wird eine Message generiert und
         * an alle Clients geschickt
         */

        gameView.chatWindow.getSendButton().setOnAction(event -> {
            if (gameView.chatWindow.getTxtMessage().getText() == null || gameView.chatWindow.getTxtMessage().getText().trim().isEmpty()) {
            }else {
                String text = gameView.chatWindow.getMessage();
                Message message = new Message(1, client.getClientName(), text, Client.getColor());
                client.sendObject(message);
                gameView.chatWindow.clearMessageField();
            }
        });

        gameView.chatWindow.getTxtMessage().setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                gameView.chatWindow.getSendButton().fire();
            }
        });

        /**
         * Eventhändler für den Moneybutton. Es werden alle Geldkarten ausgespielt und die dafür notwendigen Updates
         * beim Spieler sowie auch beim GUI vorgenommen
         */

        gameView.moneyButton.setOnAction(event -> {
            if(gameModel.getPlayer().isYourTurn() && gameModel.getPlayer().isBuyPhase()){
                ArrayList<Card> tempList = gameModel.getPlayer().playAllMoneyCards();

                for(Card card : tempList){
                    noteFlowUpdate(card, gameModel.getPlayer(), 0, Client.getColor());
                    playSound(soundUpdate(card.getName()));
                    GameObject gObject = new GameObject(gameModel.getPlayer(), card, 0 );
                    this.client.sendObject(gObject);
                }

                player1LabelUpdate();
                this.handCardController = fieldCardController.getHandCardController();
                handCardController.updateHandCardsView();
                fieldCardController.fieldCardsGlowingUpdate(fieldCardController.getResourceButtons());
                fieldCardController.fieldCardsGlowingUpdate(fieldCardController.getActionButtons());
            }
        });

        /**
         * Eventhanlder für das drücken des Musik Buttons
         */
        gameView.musicButton.setOnAction(event -> {
            if (client.getMusicActivated()){
                client.stopMusic();
                client.setMusicActivated(false);
                Platform.runLater(() -> gameView.musicButton.getStyleClass().clear());
                Platform.runLater(() -> gameView.musicButton.getStyleClass().add("musicButtonOff"));
            } else {
                client.startBackground();
                client.setMusicActivated(true);
                Platform.runLater(() -> gameView.musicButton.getStyleClass().clear());
                Platform.runLater(() -> gameView.musicButton.getStyleClass().add("musicButtonOn"));
            }
        });
    }


    /**
     * Methode um den PutStapel zu aktualisieren. Anhand des mitgegebenen Spielers sowie des Buttons wird die Karte
     * neu gesetzt oder unsichtbar gemacht.
     */

    public void putStapelUpdate(Player player, Button putStapelButton){
        if(player.getPutDeck().size() > 0) {
            putStapelButton.getStyleClass().clear();
            putStapelButton.getStyleClass().add("mediumButton");
            putStapelButton.getStyleClass().add(player.getPutDeck().get(player.getPutDeck().size() - 1).getName());
        }else{
            if(player.getPlayerName().equals(gameModel.getPlayer().getPlayerName())){
                putStapelButton.getStyleClass().clear();
                putStapelButton.getStyleClass().add("mediumButton");
                putStapelButton.getStyleClass().add("invisible");
            }else{
                putStapelButton.getStyleClass().clear();
                putStapelButton.getStyleClass().add("mediumButton");
                putStapelButton.getStyleClass().add("invisible");
            }
        }
    }

    /**
     * Methode um das Spielerlabel zu aktualisieren. Spielername, Geld, Punkte und Phase wird neu gesetzt.
     */

    public void player1LabelUpdate(){
        Player player = gameModel.getPlayer();
        gameView.moneyLabel1.setText(localisator.getResourceBundle().getString("money")+ ":\t"+player.getMoney());
        if(player.isActionPhase()){
            gameView.phaseLabel1.setText(localisator.getResourceBundle().getString("phase")+
                    ":\t" +localisator.getResourceBundle().getString( "action"));
        }else{gameView.phaseLabel1.setText(localisator.getResourceBundle().getString("phase")+
                ":\t" +localisator.getResourceBundle().getString( "buy"));
        }
    }

    /**
     * Methode um das NoteFlow zu aktualisieren. Anhand des Spielers wird die Farbe festgelegt und im Text danach die Aktion
     * beschrieben sowie die aktuell verfügbaren Käufe sowie Aktionen ausgegeben.
     */

    public void noteFlowUpdate(Card card, Player player, int type, String color) {
        Text action;

        if (type == 0) {
            action = new Text(localisator.getResourceBundle().getString("played") + " :\t");
        } else {action = new Text(localisator.getResourceBundle().getString("bought") + " :\t");
        }

        Text cardName = new Text(localisator.getResourceBundle().getString(card.getName())+ gameView.newLine);
        Text actions = new Text(localisator.getResourceBundle().getString("actions") + " :\t"+player.getActions() + gameView.newLine);
        Text buys = new Text(localisator.getResourceBundle().getString("buys") + " :\t"+player.getBuys() + gameView.newLine + gameView.newLine);

        action.setStyle(color);
        cardName.setStyle(color);
        actions.setStyle(color);
        buys.setStyle(color);
        Platform.runLater(() -> {
            gameView.noteFlow.getChildren().addAll(action, cardName, actions, buys);
        });
    }

    /**
     * Supportmethode damit das korrekte Soundfile abgespielt werden kan. Bei Geldkarten wird "coin" zurückgegeben, bei Punktekarten
     * "point" und bei den Aktionskarten der jeweilige Name
     */

    public String soundUpdate(String cardName){
        if(cardName.equals("copper") || cardName.equals("silver") || cardName.equals("gold")){
            cardName = "coin";
        }
        if(cardName.equals("estate") || cardName.equals("duchy") || cardName.equals("province")){
            cardName = "point";
        }
        return cardName;
    }


    /**
     * Methode um den gegnerischen Spielzug zu verarbeiten. Anhand des übermittelten GameObjects und der darin enthaltenen
     * Aktion werden die entsprechenden Aktualisierungsmethoden für das Spiel sowie das GUI aufgerufen.
     */

    public void otherPlayerChecker(GameObject gameObject) {
        if(!gameObject.getPlayer().getPlayerName().equals(gameModel.getPlayer().getPlayerName())){
            if(gameObject.getAction() == 0){
                Platform.runLater(() -> {
                    player2HandCardsUpdate(gameObject);
                });

            }else{
                Platform.runLater(() -> {
                    fieldCardUpdate(gameObject);
                });

            }

            Platform.runLater(() -> {
                player2LabelUpdate(gameObject);
                noteFlowUpdate(gameObject.getCard(), gameObject.getPlayer(), gameObject.getAction(), gameObject.getColor());
                playSound(soundUpdate(gameObject.getCard().getName()));
                putStapelUpdate(gameObject.getPlayer(), gameView.putStapelPlayer2);
            });


            if (gameObject.getCard().getType().equals("point")){
                Platform.runLater(() -> gameView.pointLabel2.setText(localisator.getResourceBundle().getString("point")+ ":\t" + gameObject.getPlayer().getPoints()));
            }
    }
    }

    /**
     * Methode um den gegnerischen Handkarten zu aktualisieren. Ausgespielte Karten werden angezeigt wohingegen man bei
     * den noch verbleibenden Handkarten lediglich die Rückseite sieht.
     */

    public void player2HandCardsUpdate(GameObject gameObject) {
            gameView.player2Box.getChildren().clear();

                for (int i = 0; i < gameObject.getPlayer().getHandCards().size() + gameObject.getPlayer().getPlayDeck().size(); i++) {

                    if (i >= gameObject.getPlayer().getPlayDeck().size()) {
                        Button button = new Button();
                        button.getStyleClass().add("mediumButton");
                        button.getStyleClass().add("back2");
                        gameView.player2Box.getChildren().add(button);

                        if (getGameView().playerLabel1.getText().equals(gameView.playerLabel2.getText())) {
                            button.getStyleClass().add("invisible");
                        }
                    } else {
                        GameButton gameButton = new GameButton(gameObject.getPlayer().getPlayDeck().get(i));
                        gameView.player2Box.getChildren().add(gameButton);
                        if (getGameView().playerLabel1.getText().equals(gameView.playerLabel2.getText())) {
                            gameButton.getStyleClass().add("invisible");
                        }
                    }
                }
    }

    /**
     * Supportmethoe um anhand dem GameObject bzw der vom Gegenspieler gekaufte Karte das korrekte Update ausführen
     * zu können.
     */

    public void fieldCardUpdate(GameObject gameObject){
        String type = gameObject.getCard().getType();

        switch (type) {
            case "action":
                actionFieldCardUpdate(gameObject.getCard().getName());
                break;
            case "money":
                resourceFieldUpdate(gameObject.getCard().getName());
                break;
            case "point":
                resourceFieldUpdate(gameObject.getCard().getName());
                break;
            default:
                break;
        }
    }

    /**
     * Aktualisiert die Aktionskarten auf dem Spielfeld nach dem gegnerischen Kauf.
     */

    public void actionFieldCardUpdate(String cardName){
            for (GameButton card : fieldCardController.getActionButtons()){
                if (card.getCard().getName().equals(cardName)){
                    card.setAmount(card.getAmount()-1);
                    Platform.runLater(() -> card.setText("" + card.getAmount()));
                }
            }
    }

    /**
     * Aktualisiert die Geld oder Punktekarten auf dem Spielfeld nach dem gegnerischen Kauf.
     */

    public void resourceFieldUpdate(String cardName){
        for (GameButton card : fieldCardController.getResourceButtons()){
            if (card.getCard().getName().equals(cardName)){
                card.setAmount(card.getAmount() - 1);
                Platform.runLater(() -> card.setText("" + card.getAmount()));
            }
        }
    }

    /**
     * Aktualisiert die Label des gegnerischen Spielers
     */

    public void player2LabelUpdate(GameObject gameObject){

        Platform.runLater(() -> {

            if(!getGameView().playerLabel1.getText().equals(gameView.playerLabel2.getText())) {
                gameView.playerLabel2.getStyleClass().remove("invisible");
                gameView.moneyLabel2.getStyleClass().remove("invisible");
                gameView.phaseLabel2.getStyleClass().remove("invisible");
                gameView.pointLabel2.getStyleClass().remove("invisible");
            }

            gameView.playerLabel2.setText(gameObject.getPlayer().getPlayerName());

            gameView.moneyLabel2.setText(localisator.getResourceBundle().getString("money")+ ":\t"+gameObject.getPlayer().getMoney());
            gameView.pointLabel2.setText(localisator.getResourceBundle().getString("point")+ ":\t"+gameObject.getPlayer().getPoints());
            if(gameModel.getPlayer().isActionPhase()){
                gameView.phaseLabel2.setText(localisator.getResourceBundle().getString("phase")+
                        ":\t" +localisator.getResourceBundle().getString( "action"));
            }else{gameView.phaseLabel2.setText(localisator.getResourceBundle().getString("phase")+
                    ":\t" +localisator.getResourceBundle().getString( "buy"));
            }
        });
    }

    /**
     * Methode wird nach abschluss des gegnerischen Spielzugs aufgerufen und startet den neuen Zug. Zudem werden die
     * Handkarten des gegnerischen Spielers aktualisiert.
     */

    public void newTurn(boolean turn){
        gameModel.getPlayer().setYourTurn(turn);
        Platform.runLater(() ->{

        fieldCardController.getHandCardController().updateHandCardsView();

            if(!getGameView().playerLabel1.getText().equals(gameView.playerLabel2.getText())) {
                gameView.player2Box.getChildren().clear();
                for(int i=0;i<5;i++){
                    Button player2Card=new Button();
                    player2Card.getStyleClass().add("mediumButton");
                    player2Card.getStyleClass().add("back2");
                    gameView.player2Box.getChildren().add(player2Card);
                }
            }
        });
    }

    /**
     * Methode um nach abschluss des Spiels die EndView aufzurufen
     */

    public void endView(ArrayList<Player> o) {
        Platform.runLater(() ->{
            endView = new EndView(gameView.getGameStage(), localisator, gameView.turnLabel.getText(), client.getResolution());
            endModel = new EndModel();
            endController = new EndController(endModel, endView, localisator, o, gameModel, this);
        } );
    }

    /**
     * Methode um die Labels mit dem aktuellen Spieler und der Anzahl Züge zu aktualisieren
     */

    public void changeTurnLabels(String name, int i){
        Platform.runLater(() -> {
            gameView.playerLabel.setText(localisator.getResourceBundle().getString("player")+ ":\t" + name);
            gameView.turnLabel.setText(localisator.getResourceBundle().getString("round")+ ":\t" + i);
        });
    }

    /**
     * Methode um di Labels des Gegenspielers zu aktualisieren
     */

    public void player2PointUpdate(Player o){
        Platform.runLater(() -> {
            gameView.playerLabel2.setText(o.getPlayerName());
            gameView.pointLabel2.setText(localisator.getResourceBundle().getString("point") + ":\t" + o.getPoints());
            putStapelUpdate(o, gameView.putStapelPlayer2);
        });
    }

    /**
     * Methode um ein Audio File abzuspielen.
     * Kopiert von: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
     * @param fileName
     */

    public static synchronized void playSound(final String fileName) {
        if (client.getMusicActivated()) {
            new Thread(() -> {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream("/Sounds/" + fileName + ".wav"));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                }
            }).start();
        }
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public GameView getGameView() {
        return gameView;
    }

    public Client getClient(){
        return this.client;
    }

    public FieldCardController getFieldCardController() {
        return fieldCardController;
    }

    public void endGame() {
        gameModel.getPlayer().setYourTurn(false);
    }

    public Localisator getLocalisator() {
        return localisator;
    }
}
