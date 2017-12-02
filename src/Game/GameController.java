package Game;

import Client_Server.Chat.Message;
import Client_Server.Client.Client;
import Client_Server.GameObject;
import End.EndController;
import End.EndModel;
import Localisation.Localisator;
import Login.EndView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import sun.applet.Main;

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


        gameView.endGameButton.setOnAction(event -> {
            endView = new EndView(gameView.getGameStage(), localisator);
            endModel = new EndModel();
            endController = new EndController(endModel, endView, localisator);

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
                playSound("coin.wav");
                ArrayList<Card> tempList = gameModel.getPlayer().playAllMoneyCards();

                for(Card card : tempList){
                    noteFlowUpdate(card, gameModel.getPlayer(), 0, Client.getColor());
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


    public void otherPlayerChecker(GameObject gameObject) {
        if(!gameObject.getPlayer().getPlayerName().equals(gameModel.getPlayer().getPlayerName())){
            if(gameObject.getAction() == 0){
                player2HandCardsUpdate(gameObject);
            }else{
                fieldCardUpdate(gameObject);
            }
            player2LabelUpdate(gameObject);
            noteFlowUpdate(gameObject.getCard(), gameObject.getPlayer(), gameObject.getAction(), gameObject.getColor());
            if (gameObject.getCard().getType().equals("point")){
                gameView.pointLabel2.setText(localisator.getResourceBundle().getString("point")+ ":\t" + gameObject.getPlayer().getPoints());
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

        Platform.runLater(() -> {
            for(int i = 0; i< fieldCardController.getActionButtons().size();i++){
                if(fieldCardController.getActionButtons().get(i).getCard().getName().equals(cardName)){
                    fieldCardController.getActionButtons().get(i).setAmount( fieldCardController.getActionButtons().get(i).getAmount()-1);
                }
            }

        });

    }

    public void resourceFieldUpdate(String cardName){

        Platform.runLater(() -> {
            for(int i = 0; i< fieldCardController.getResourceButtons().size();i++){
                if(fieldCardController.getResourceButtons().get(i).getCard().getName().equals(cardName)){
                    fieldCardController.getResourceButtons().get(i).setAmount( fieldCardController.getResourceButtons().get(i).getAmount()-1);
                }
            }

        });


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
            endView = new EndView(gameView.getGameStage(), localisator);
            endModel = new EndModel();
            endController = new EndController(endModel, endView, localisator, o, gameModel, this);
        } );


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
                        Main.class.getResourceAsStream("/Sounds/" + fileName));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
