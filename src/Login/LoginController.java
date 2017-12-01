package Login;

import Client_Server.Client.Client;
import Client_Server.Chat.Message;
import Client_Server.Server.StartServer;
import End.EndController;
import Lobby.LobbyController;
import Lobby.LobbyModel;
import Lobby.LobbyView;
import Localisation.Localisator;
import javafx.stage.Stage;

import java.util.Optional;

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
                Optional<String> address = loginView.dialog.showAndWait();
                if (address.isPresent()){
                    if (address.get().equals("localhost") || loginModel.checkIP(address.get())){

                        clientName = loginView.userNameField.getText();
                        loginView.connectingLabel.setVisible(true);
                        client = new Client(address.get(), clientName);
                        if (client.isConnected()) {
                            client.start();
                        }
                        loginView.connectingLabel.setVisible(false);

                        if (!client.isFailure()) {
                            Message user = new Message(0, clientName, "login");
                            client.sendObject(user);
                            while (!client.isChecked()) {
                                //Waiting until server response for username validation
                            }
                            if (client.isValid()) {
                                lobbyView = new LobbyView(primaryStage, localisator);
                                lobbyController = new LobbyController(lobbyModel, lobbyView, localisator, client);
                                lobbyModel = new LobbyModel();
                                client.setLobbyController(lobbyController);
                                client.actualizePlayers();
                            } else {
                                loginView.userNameField.setPromptText(localisator.getResourceBundle().getString("validUsername"));
                                loginView.userNameField.getStyleClass().clear();
                                loginView.userNameField.getStyleClass().add("text-field");
                                client.resetChecked();
                            }
                        } else {
                            loginView.conError.show();
                        }
                    }
                }
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
                client = new Client("localhost", clientName);
                client.start();
                client.sendObject(new Message(0, clientName, "login"));
                lobbyView = new LobbyView(primaryStage, localisator);
                lobbyModel = new LobbyModel();
                lobbyController = new LobbyController(lobbyModel, lobbyView, localisator, client);
                client.setLobbyController(lobbyController);
                client.actualizePlayers();
                client.setServer();
                lobbyController.showAddress();
            }
        });

        loginView.switchBox.setOnAction(event -> {
            String language = loginView.switchBox.getValue();
            languageChecker(language);
            languageUpdate();
        });
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
            loginView.switchBox.setPromptText(localisator.getResourceBundle().getString("language"));
            loginView.connectingLabel.setText(localisator.getResourceBundle().getString("connecting"));
            loginView.dialog.setTitle(localisator.getResourceBundle().getString("addressTitle"));
            loginView.dialog.setHeaderText(localisator.getResourceBundle().getString("addressHeader"));
            loginView.dialog.setContentText(localisator.getResourceBundle().getString("addressText"));
            loginView.conError.setTitle(localisator.getResourceBundle().getString("conErrorTitle"));
            loginView.conError.setHeaderText(localisator.getResourceBundle().getString("conErrorHeader"));
            loginView.conError.setContentText(localisator.getResourceBundle().getString("conErrorText"));
        }

}



