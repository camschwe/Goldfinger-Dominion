package Lobby;

import Client_Server.Chat.Message;
import Client_Server.Client.Client;
import Game.*;
import Localisation.Localisator;
import Login.LoginController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Benjamin Probst on 06.10.2017.
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
    private GameController gameController;

    public LobbyController(LobbyModel lobbyModel, LobbyView lobbyView, Localisator localisator, Client client) {
        this.lobbyModel = lobbyModel;
        this.lobbyView = lobbyView;
        this.localisator = localisator;
        this.client = client;

        lobbyView.startButton.setOnAction(event -> {

            //TODO: Add player count
            if (!client.isGameStarted()) {
                if (client.isServer()) {
                    client.sendObject(new Message(4, client.getClientName(), "start"));
                } else {
                    if (lobbyView.startButton.getText().equals(localisator.getResourceBundle().getString("start"))) {
                        client.sendObject(new Message(1, client.getClientName(), "ready", Client.getColor()));
                        lobbyView.startButton.setText(localisator.getResourceBundle().getString("ready"));
                    } else {
                        client.sendObject(new Message(1, client.getClientName(), "unready", Client.getColor()));
                        lobbyView.startButton.setText(localisator.getResourceBundle().getString("start"));
                    }
                }
            }
        });

        lobbyView.spectatorButton.setOnAction(event -> {
            if (client.isGameStarted()){
                startGame();
            }else{
                Alert quoteText = new Alert(Alert.AlertType.INFORMATION);
                quoteText.setTitle(localisator.getResourceBundle().getString("note"));
                quoteText.setHeaderText((localisator.getResourceBundle().getString("visitor")));
                quoteText.setContentText(localisator.getResourceBundle().getString("visitorInfo"));
                quoteText.showAndWait();
            }
        });

        lobbyView.chatWindow.getSendButton().setOnAction(event -> {
            if (lobbyView.chatWindow.getTxtMessage().getText() == null || lobbyView.chatWindow.getTxtMessage().getText().trim().isEmpty()) {
            }else {
                String text = lobbyView.chatWindow.getMessage();
                Message message = new Message(1, client.getClientName(), text, Client.getColor());
                client.sendObject(message);
                lobbyView.chatWindow.clearMessageField();
            }
        });

        lobbyView.chatWindow.getTxtMessage().setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                lobbyView.chatWindow.getSendButton().fire();
            }
        });

        lobbyView.primaryStage.setOnCloseRequest(event -> {
            client.stopClient();
            Platform.exit();
            System.exit(0);
        });

        lobbyView.musicButton.setOnAction(event -> {
            if (client.getMusicActivated()){
                client.stopMusic();
                client.setMusicActivated(true);
                Platform.runLater(() -> lobbyView.musicButton.getStyleClass().clear());
                Platform.runLater(() -> lobbyView.musicButton.getStyleClass().add("musicButtonOff"));
            } else {
                client.startBackground();
                client.setMusicActivated(false);
                Platform.runLater(() -> lobbyView.musicButton.getStyleClass().clear());
                Platform.runLater(() -> lobbyView.musicButton.getStyleClass().add("musicButtonOff"));
            }
        });


    }

    public LobbyView getLobbyView() {
        return lobbyView;
    }

    public LobbyModel getLobbyModel() {
        return lobbyModel;
    }

    public void startGame(){
        Stage gameStage = new Stage();
        gameView = new GameView(gameStage, localisator, lobbyView.getChatWindow(), client.getResolution(), localisator.getLanguage());
        gameModel = new GameModel(client);
        gameController = new GameController(gameView, localisator, gameModel, client);
        client.setGameController(gameController);

        gameView.start();
        lobbyView.stop();
    }

    public void showAddress(){
        if (client.isServer()){
            try {
                //lobbyView.addressLabel.setText(InetAddress.getLocalHost());
                System.out.println(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

}
