package Game;

import Client_Server.Client.Client;
import Localisation.Localisator;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

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

        fieldCardController = new FieldCardController(gameView,localisator, gameModel, this);



        gameView.gameStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
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

    public void updateLabel(){
        Player player = gameModel.getPlayer();
        gameView.moneyLabel1.setText(localisator.getResourceBundle().getString("money")+ ":\t"+player.getMoney());
        if(player.isActionPhase()){
            gameView.phaseLabel1.setText(localisator.getResourceBundle().getString("phase")+
                    ":\t" +localisator.getResourceBundle().getString( "action"));
        }else{gameView.phaseLabel1.setText(localisator.getResourceBundle().getString("phase")+
                ":\t" +localisator.getResourceBundle().getString( "buy"));
        }
    }


    public void noteFlowUpdate(Card card, Player player, int type) {
        Text action;

        if (type == 0) {
            action = new Text(localisator.getResourceBundle().getString("played") + " :\t");
        } else {action = new Text(localisator.getResourceBundle().getString("bought") + " :\t");
        }

        Text cardName = new Text(localisator.getResourceBundle().getString(card.getName())+ gameView.newLine);
        Text actions = new Text(localisator.getResourceBundle().getString("actions") + " :\t"+player.getActions() + gameView.newLine);
        Text buys = new Text(localisator.getResourceBundle().getString("buys") + " :\t"+player.getBuys() + gameView.newLine + gameView.newLine);

        gameView.noteFlow.getChildren().addAll(action, cardName, actions, buys);
    }


    public GameView getGameView() {
        return gameView;
    }
}
