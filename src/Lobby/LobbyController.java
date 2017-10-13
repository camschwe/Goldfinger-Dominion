package Lobby;

import Game.CardHandle;
import Game.GameController;
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

    public LobbyController(LobbyModel lobbyModel, LobbyView lobbyView, Localisator localisator) {
        this.lobbyModel = lobbyModel;
        this.lobbyView = lobbyView;
        this.localisator = localisator;

        lobbyView.startButton.setOnAction(event -> {

            Stage gameStage = new Stage();
            gameView = new GameView(gameStage, localisator);
            gameController = new GameController(gameView, localisator);
            cardHandle = new CardHandle(gameView);
            gameView.start();
        });
    }
}
