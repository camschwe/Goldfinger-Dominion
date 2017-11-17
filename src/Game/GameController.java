package Game;

import Client_Server.Chat.Message;
import Client_Server.Client.Client;
import Client_Server.GameObject;
import Localisation.Localisator;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

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
    private  HandCardController handCardController;
    private FieldCardController fieldCardController;
    private Client client;

    public GameController(GameView gameView, Localisator localisator, GameModel gameModel, Client client){
        this.gameView = gameView;
        this.localisator = localisator;
        this.gameModel = gameModel;
        this.client = client;

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
                handCardController.updateHandcardsView();
                player1LabelUpdate();
                fieldCardController.fieldCardsGlowingUpdate(fieldCardController.getResourceButtons());
                fieldCardController.fieldCardsGlowingUpdate(fieldCardController.getActionButtons());
            }
            if (gameModel.getPlayer().isTurnEnded()){
                getClient().sendObject(new Message(6, gameModel.getPlayer().getPlayerName(), "Turn ended"));
                gameModel.getPlayer().setTurnEnded(false);
            }

        });

        //Eventhändler für den Money Button
        //TODO: fix HANDCARDUPDATE
        gameView.moneyButton.setOnAction(event -> {
            if(gameModel.getPlayer().isYourTurn() && gameModel.getPlayer().isBuyPhase()){
                ArrayList<Card> tempList = gameModel.getPlayer().playAllMoneyCards();

                for(Card card : tempList){
                    noteFlowUpdate(card, gameModel.getPlayer(), 0, Client.getColor());
                    GameObject gObject = new GameObject(gameModel.getPlayer(), card, 0 );
                    this.client.sendObject(gObject);

                }

                player1LabelUpdate();
                handCardController.updateHandcardsView();
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
    }
    }


    public void player2HandCardsUpdate(GameObject gameObject) {
        Platform.runLater(() -> {
            gameView.player2Box.getChildren().clear();

            for (int i = 0; i < gameObject.getPlayer().getHandCards().size() + gameObject.getPlayer().getPlayDeck().size(); i++) {
                System.out.println(gameObject.getPlayer().getPlayDeck().size());

                if(i >= gameObject.getPlayer().getPlayDeck().size()){
                    Button button = new Button();
                    button.getStyleClass().add("mediumButton");
                    button.getStyleClass().add("back2");
                    gameView.player2Box.getChildren().add(button);
                }else {
                    GameButton gameButton = new GameButton(gameObject.getPlayer().getPlayDeck().get(i));
                    gameView.player2Box.getChildren().add(gameButton);
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
            gameView.playerLabel2.setText(gameObject.getPlayer().getPlayerName());

            gameView.moneyLabel2.setText(localisator.getResourceBundle().getString("money")+ ":\t"+gameObject.getPlayer().getMoney());
            if(gameModel.getPlayer().isActionPhase()){
                gameView.phaseLabel2.setText(localisator.getResourceBundle().getString("phase")+
                        ":\t" +localisator.getResourceBundle().getString( "action"));
            }else{gameView.phaseLabel2.setText(localisator.getResourceBundle().getString("phase")+
                    ":\t" +localisator.getResourceBundle().getString( "buy"));
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
}
