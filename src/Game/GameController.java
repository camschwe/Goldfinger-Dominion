package Game;

import Client_Server.Client.Client;
import Localisation.Localisator;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * Created by camillo.schweizer on 21.10.2017.
 */


public class GameController {

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

        handCardController = new HandCardController(gameView, localisator, gameModel, this);
        fieldCardController = new FieldCardController(gameView,localisator, gameModel, handCardController, this);

        gameView.gameStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }


    public void putStapelUpdate(Player player, Button putStapelButton){
        if(player.getPutDeck().size() > 0) {
            putStapelButton.getStyleClass().removeAll();
            putStapelButton.getStyleClass().add(player.getPutDeck().get(player.getPutDeck().size()-1).getCardName());
            System.out.println("changed to:"+ player.getPutDeck().get(player.getPutDeck().size()-1).getCardName() );
        }else{putStapelButton.getStyleClass().add("trash");
        }


    }


}
