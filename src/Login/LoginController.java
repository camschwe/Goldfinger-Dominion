package Login;

import Client_Server.Client.Client;
import Client_Server.Chat.Message;
import Client_Server.Server.StartServer;
import Dialogs.DialogController;
import Dialogs.DialogModel;
import Dialogs.DialogView;
import Lobby.LobbyController;
import Lobby.LobbyModel;
import Lobby.LobbyView;
import Localisation.Localisator;
import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import static javafx.scene.media.AudioClip.INDEFINITE;

/**
 * Created by Benjamin Probst on 06.10.2017.
 */

public class LoginController {

    private LoginModel loginModel;
    private LoginView loginView;
    private LobbyView lobbyView;
    private Stage primaryStage;
    private LobbyController lobbyController;
    private LobbyModel lobbyModel;
    protected Localisator localisator;
    private String clientName;
    private Client client;
    private String resolution = "1080p";


    public LoginController(LoginModel loginModel, LoginView loginView, Stage primaryStage, Localisator localisator) {
        this.loginModel = loginModel;
        this.loginView = loginView;
        this.primaryStage = primaryStage;
        this.localisator = localisator;

        loginView.joinButton.setOnAction(event -> {

            if (loginView.userNameField.getText() == null || loginView.userNameField.getText().trim().isEmpty()){
                loginView.userNameField.setPromptText(localisator.getResourceBundle().getString("UsernameNeeded"));
                loginView.userNameField.getStyleClass().clear();
                loginView.userNameField.getStyleClass().add("userNameNeeded");
            } else {
                DialogView dialogView = new DialogView("Please enter IP Address", "localhost", "IP Address", localisator);
                DialogController dialogController = new DialogController(dialogView, new DialogModel(), localisator, this);
                dialogView.start();

            }
        });

        loginView.hostButton.setOnAction(event -> {

            if (loginView.userNameField.getText() == null || loginView.userNameField.getText().trim().isEmpty()) {
                loginView.userNameField.setPromptText(localisator.getResourceBundle().getString("UsernameNeeded"));
                loginView.userNameField.getStyleClass().clear();
                loginView.userNameField.getStyleClass().add("userNameNeeded");
            } else {
                clientName = loginView.userNameField.getText();
                StartServer startServer = new StartServer();
                startServer.start();
                client = new Client("localhost", clientName, resolution);
                client.start();
                client.sendObject(new Message(0, clientName, "login"));
                lobbyView = new LobbyView(primaryStage, localisator);
                lobbyController = new LobbyController(lobbyModel, lobbyView, localisator, client);
                lobbyModel = new LobbyModel();
                client.setLobbyController(lobbyController);
                client.actualizePlayers();
                client.setServer();
                lobbyController.showAddress();
            }
        });

        loginView.languageBox.setOnAction(event -> {
            String language = loginView.languageBox.getValue();
            languageChecker(language);
            languageUpdate();
        });

        loginView.sizeBox.setOnAction(event -> {
            this.resolution = loginView.sizeBox.getValue();
        });

        /**
         * Methode um ein Audio File abzuspielen.
         * Kopiert von: https://stackoverflow.com/questions/31784698/javafx-background-thread-task-should-play-music-in-a-loop-as-background-thread
         * @param fileName
         */
        final Task task = new Task() {

            @Override
            protected Object call() throws Exception {
                int s = INDEFINITE;
                AudioClip audio = new AudioClip(getClass().getResource("/Sounds/background.wav").toExternalForm());
                audio.setVolume(0.07);
                audio.setCycleCount(s);
                audio.play();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void languageChecker(String language) {

        switch (language) {
            case "Schwiizerdütsch":
                localisator.switchCH();
                break;
            case "Deutsch":
                localisator.switchGER();
                break;
            case "English":
                localisator.switchENG();
                break;
            default:
                break;
        }
    }

    public void languageUpdate(){

        loginView.userNameLabel.setText(localisator.getResourceBundle().getString("username"));
        loginView.hostButton.setText(localisator.getResourceBundle().getString("hosting"));
        loginView.joinButton.setText(localisator.getResourceBundle().getString("join"));
        loginView.userNameField.setPromptText(localisator.getResourceBundle().getString("username"));
        loginView.userNameField.getStyleClass().clear();
        loginView.userNameField.getStyleClass().add("text-field");
        loginView.languageBox.setPromptText(localisator.getResourceBundle().getString("language"));
        loginView.sizeBox.setPromptText(localisator.getResourceBundle().getString("size"));
        loginView.connectingLabel.setText(localisator.getResourceBundle().getString("connecting"));
        loginView.dialog.setTitle(localisator.getResourceBundle().getString("addressTitle"));
        loginView.dialog.setHeaderText(localisator.getResourceBundle().getString("addressHeader"));
        loginView.dialog.setContentText(localisator.getResourceBundle().getString("addressText"));
        loginView.conError.setTitle(localisator.getResourceBundle().getString("conErrorTitle"));
        loginView.conError.setHeaderText(localisator.getResourceBundle().getString("conErrorHeader"));
        loginView.conError.setContentText(localisator.getResourceBundle().getString("conErrorText"));
    }

    public String connect(String address){
        clientName = loginView.userNameField.getText();

        client = new Client(address, clientName, resolution);
        if (client.isConnected()) {
            client.start();
        }
        loginView.connectingLabel.setVisible(false);

        if (!client.isFailure()) {
            Message user = new Message(0, clientName, "login");
            client.sendObject(user);
            while (!client.isChecked()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Waiting until server response for username validation
            }
            if (client.isValid()) {
                lobbyView = new LobbyView(primaryStage, localisator);
                lobbyController = new LobbyController(lobbyModel, lobbyView, localisator, client);
                lobbyModel = new LobbyModel();
                client.setLobbyController(lobbyController);
                client.actualizePlayers();
                return "successful";
            } else {
                loginView.userNameField.setPromptText(localisator.getResourceBundle().getString("validUsername"));
                loginView.userNameField.getStyleClass().clear();
                loginView.userNameField.getStyleClass().add("text-field");
                client.resetChecked();
                return "username used";
            }

        } else {
            return "Error connecting";
        }
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }

    public LoginView getLoginView() {
        return loginView;
    }
}



