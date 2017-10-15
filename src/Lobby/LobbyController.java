package Lobby;

import Client_Server.Chat.Message;
import Client_Server.Client.Client;
import Game.CardHandle;
import Game.GameController;
import Game.GameModel;
import Game.GameView;
import Localisation.Localisator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private Client client;

    public LobbyController(LobbyModel lobbyModel, LobbyView lobbyView, Localisator localisator, Client client) {
        this.lobbyModel = lobbyModel;
        this.lobbyView = lobbyView;
        this.localisator = localisator;
        this.client = client;

        //client.setLobbyController(this);

        lobbyView.startButton.setOnAction(event -> {

            //TODO: Add player count

            Stage gameStage = new Stage();
            gameView = new GameView(gameStage, localisator);
            gameModel = new GameModel(2);
            gameController = new GameController(gameView, localisator, gameModel);
            cardHandle = new CardHandle(gameView);
            gameView.start();
        });

        lobbyView.chatWindow.getSendButton().setOnAction(event -> {
            String text = lobbyView.chatWindow.getMessage();
            Message message = new Message(1, client.getClientName(), text);
            client.sendObject(message);
            lobbyView.chatWindow.clearMessageField();
        });

        lobbyView.chatWindow.getTxtMessage().setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                lobbyView.chatWindow.getSendButton().fire();
            }
        });


    }

    public LobbyView getLobbyView() {
        return lobbyView;
    }

    public LobbyModel getLobbyModel() {
        return lobbyModel;
    }
}
