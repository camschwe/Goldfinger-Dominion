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
    private Client client;
    private EndView endView;
    private EndController endController;
    private EndModel endModel;



    public GameController(GameView gameView, Localisator localisator, GameModel gameModel, Client client){
        this.gameView = gameView;
        this.localisator = localisator;
        this.gameModel = gameModel;
        this.client = client;

        gameView.pointLabel1.setText(localisator.getResourceBundle().getString("point")+ ":\t" + gameModel.getPlayer().getPoints());

        if(gameModel.getPlayerList().size()== 1){
            gameView.player2Box.setVisible(false);
            gameView.playerLabel2.getStyleClass().add("invisible");
            gameView.putStapelPlayer2.getStyleClass().add("invisible");
            gameView.drawStapelPlayer2.getStyleClass().add("invisible");
            gameView.pointLabel2.getStyleClass().add("invisible");
            gameView.phaseLabel2.getStyleClass().add("invisible");
            gameView.moneyLabel2.getStyleClass().add("invisible");
        }

        if(localisator.getLanguage().equals("eng")){
            gameView.getScene().getStylesheets().clear();
            gameView.getScene().getStylesheets().add(getClass().getResource("../Stylesheets/GameStylesEng.css").toExternalForm());
        }

        if(client.getResolution().equals("720p")){
            smallScreenUpdate();
        }

        fieldCardController = new FieldCardController(gameView, localisator, gameModel, this);
        gameView.playerLabel1.setText(client.getClientName());

        gameView.gameStage.setOnCloseRequest(event -> {
            client.stopClient();
            Platform.exit();
            System.exit(0);
        });

        //Eventhändler für den Phase Button
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

        //Eventhändler für den Money Button
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

        gameView.musicButton.setOnAction(event -> {
            if (client.getMusicActivated()){
                client.stopMusic();
                client.setMusicActivated(true);
                Platform.runLater(() -> gameView.musicButton.getStyleClass().clear());
                Platform.runLater(() -> gameView.musicButton.getStyleClass().add("musicButtonOff"));
            } else {
                client.startBackground();
                client.setMusicActivated(false);
                Platform.runLater(() -> gameView.musicButton.getStyleClass().clear());
                Platform.runLater(() -> gameView.musicButton.getStyleClass().add("musicButtonOff"));
            }
        });
    }


    /**
     * Methoden zum GUI Update
     */

    public void putStapelUpdate(Player player, Button putStapelButton){
        if(player.getPutDeck().size() > 0) {
            putStapelButton.getStyleClass().clear();
            putStapelButton.getStyleClass().add("mediumButton");
            putStapelButton.getStyleClass().add(player.getPutDeck().get(player.getPutDeck().size()-1).getName());
        }else{putStapelButton.getStyleClass().add("trash");
        }


    }

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

    public String soundUpdate(String cardName){
        if(cardName.equals("copper") || cardName.equals("silver") || cardName.equals("gold")){
            cardName = "coin";
        }
        if(cardName.equals("estate") || cardName.equals("duchy") || cardName.equals("province")){
            cardName = "point";
        }
        return cardName;
    }



    public void otherPlayerChecker(GameObject gameObject) {
        if(!gameObject.getPlayer().getPlayerName().equals(gameModel.getPlayer().getPlayerName())){
            if(gameObject.getAction() == 0){
                player2HandCardsUpdate(gameObject);
            }else{
                fieldCardUpdate(gameObject);
            }
            player2LabelUpdate(gameObject);
            noteFlowUpdate(gameObject.getCard(), gameObject.getPlayer(), gameObject.getAction(), gameObject.getColor());
            playSound(soundUpdate(gameObject.getCard().getName()));

            if (gameObject.getCard().getType().equals("point")){
                Platform.runLater(() -> gameView.pointLabel2.setText(localisator.getResourceBundle().getString("point")+ ":\t" + gameObject.getPlayer().getPoints()));
            }
    }
    }


    public void player2HandCardsUpdate(GameObject gameObject) {
        Platform.runLater(() -> {
            gameView.player2Box.getChildren().clear();

                for (int i = 0; i < gameObject.getPlayer().getHandCards().size() + gameObject.getPlayer().getPlayDeck().size(); i++) {
                    System.out.println(gameObject.getPlayer().getPlayDeck().size());

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

            if(!getGameView().playerLabel1.getText().equals(gameView.playerLabel2.getText())) {
                gameView.drawStapelPlayer2.getStyleClass().remove("invisible");
                gameView.putStapelPlayer2.getStyleClass().remove("invisible");
                if(gameObject.getPlayer().getPutDeck().size() > 0) {
                    gameView.putStapelPlayer2.getStyleClass().add(gameObject.getPlayer().getPutDeck().get(gameObject.getPlayer().getPutDeck().size() - 1).getName());
                }
            }

        });


    }

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

    public void actionFieldCardUpdate(String cardName){
            for (GameButton card : fieldCardController.getActionButtons()){
                if (card.getCard().getName().equals(cardName)){
                    card.setAmount(card.getAmount()-1);
                    Platform.runLater(() -> card.setText("" + card.getAmount()));
                }
            }
    }

    public void resourceFieldUpdate(String cardName){
        for (GameButton card : fieldCardController.getResourceButtons()){
            if (card.getCard().getName().equals(cardName)){
                card.setAmount(card.getAmount() - 1);
                Platform.runLater(() -> card.setText("" + card.getAmount()));
            }
        }
    }

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

    public void endView(ArrayList<Player> o) {
        Platform.runLater(() ->{
            endView = new EndView(gameView.getGameStage(), localisator, gameView.turnLabel.getText());
            endModel = new EndModel();
            endController = new EndController(endModel, endView, localisator, o, gameModel, this);
        } );
    }

    public void changeTurnLabels(String name, int i){
        Platform.runLater(() -> {
            gameView.playerLabel.setText(localisator.getResourceBundle().getString("player")+ ":\t" + name);
            gameView.turnLabel.setText(localisator.getResourceBundle().getString("round")+ ":\t" + i);
        });
    }

    public void endGame() {
        gameModel.getPlayer().setYourTurn(false);
    }


    /**
     * Methode um ein Audio File abzuspielen.
     * Kopiert von: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
     * @param fileName
     */
    public static synchronized void playSound(final String fileName) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Main.class.getResourceAsStream("/Sounds/" + fileName + ".wav"));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }

    public void smallScreenUpdate(){

    }

    public Localisator getLocalisator() {
        return localisator;
    }
}
