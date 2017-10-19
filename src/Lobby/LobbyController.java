package Lobby;

import Client_Server.Chat.Message;
import Client_Server.Client.Client;
import Client_Server.Server.Server;
import Game.FieldCardController;
import Game.HandCardController;
import Game.GameModel;
import Game.GameView;
import Localisation.Localisator;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class LobbyController {

    private LobbyModel lobbyModel;
    private LobbyView lobbyView;
    private HandCardController handCardController;
    private GameView gameView;
    private Localisator localisator;
    private FieldCardController fieldCardController;
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
            handCardController = new HandCardController(gameView, localisator, gameModel);
            fieldCardController = new FieldCardController(gameView,localisator, gameModel, handCardController);
            gameView.start();
        });

        lobbyView.chatWindow.getSendButton().setOnAction(event -> {
            String text = lobbyView.chatWindow.getMessage();
            Message message = new Message(1, client.getClientName(), text, client.getColor());
            client.sendObject(message);
            lobbyView.chatWindow.clearMessageField();
        });

        lobbyView.chatWindow.getTxtMessage().setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                lobbyView.chatWindow.getSendButton().fire();
            }
        });

        lobbyView.primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });


    }

    public LobbyView getLobbyView() {
        return lobbyView;
    }

    public LobbyModel getLobbyModel() {
        return lobbyModel;
    }


}
