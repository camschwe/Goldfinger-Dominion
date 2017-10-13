package Lobby;

import Game.CardHandle;
import Game.GameController;
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
    private GameController gameController;
    private GameView gameView;
    private Localisator localisator;
    private CardHandle cardHandle;
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
            gameController = new GameController(gameView, localisator, gameModel);
            cardHandle = new CardHandle(gameView);
            gameView.start();
        });
    }
}
