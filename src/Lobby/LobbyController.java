package Lobby;

import Game.GameController;
import Game.GameView.GameView;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class LobbyController {

    private LobbyModel lobbyModel;
    private LobbyView lobbyView;
    private GameController gameController;
    private GameView gameView;

    public LobbyController(LobbyModel lobbyModel, LobbyView lobbyView) {
        this.lobbyModel = lobbyModel;
        this.lobbyView = lobbyView;

        lobbyView.startButton.setOnAction(event -> {

            Stage gameStage = new Stage();
            gameView = new GameView(gameStage);
            gameController = new GameController(gameView);
            gameView.start();
        });
    }
}
