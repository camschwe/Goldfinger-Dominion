package Lobby;

import Game.FieldCardController;
import Game.HandCardController;
import Game.GameModel;
import Game.GameView;
import Localisation.Localisator;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class LobbyController {

    private LobbyModel lobbyModel;
    private LobbyView lobbyView;
    private HandCardController gameController;
    private GameView gameView;
    private Localisator localisator;
    private FieldCardController cardHandle;
    private GameModel gameModel;

    public LobbyController(LobbyModel lobbyModel, LobbyView lobbyView, Localisator localisator) {
        this.lobbyModel = lobbyModel;
        this.lobbyView = lobbyView;
        this.localisator = localisator;

        lobbyView.startButton.setOnAction(event -> {

            //TODO: Add player count

            Stage gameStage = new Stage();
            gameView = new GameView(gameStage, localisator);
            gameModel = new GameModel(2);
            gameController = new HandCardController(gameView, localisator, gameModel);
            cardHandle = new FieldCardController(gameView, gameModel);
            gameView.start();
        });
    }
}
