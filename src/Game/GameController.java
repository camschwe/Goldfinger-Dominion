package Game;

import Client_Server.Client.Client;
import Localisation.Localisator;
import javafx.application.Platform;

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

        handCardController = new HandCardController(gameView, localisator, gameModel);
        fieldCardController = new FieldCardController(gameView,localisator, gameModel, handCardController);

        gameView.gameStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }


}
