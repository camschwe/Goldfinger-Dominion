package Login;

import Client_Server.Client.Client;
import Client_Server.Chat.Message;
import Client_Server.Server.Server;
import Lobby.LobbyController;
import Lobby.LobbyModel;
import Lobby.LobbyView;
import Localisation.Localisator;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 06.10.2017.
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
    private Server server;


    public LoginController(LoginModel loginModel, LoginView loginView, Stage primaryStage, Localisator localisator) {
        this.loginModel = loginModel;
        this.loginView = loginView;
        this.primaryStage = primaryStage;
        this.localisator = localisator;

        loginView.joinButton.setOnAction(event -> {

            if (loginView.userNameField.getText() != null || !loginView.userNameField.getText().trim().isEmpty()) {
                clientName = loginView.userNameField.getText();
                if (client == null){
                    client = new Client("localhost", clientName);
                    client.start();
                }
                Message benutzer = new Message(0, clientName, "login");
                client.sendObject(benutzer);
                while (!client.isChecked()){
                    //Waiting until server response
                }
                if (client.isValid()) {
                    lobbyView = new LobbyView(primaryStage, localisator);
                    lobbyModel = new LobbyModel();
                    lobbyController = new LobbyController(lobbyModel, lobbyView, localisator);
                } else {
                    loginView.userNameValid.setVisible(true);
                    client.resetChecked();
                }
            }
        });

        loginView.hostButton.setOnAction(event -> {

            /**if (loginView.userNameField.getText() != null || !loginView.userNameField.getText().trim().isEmpty()) {
                clientName = loginView.userNameField.getText();
                server = new Server();
                server.start();
                client = new Client("localhost", clientName);
                client.start();
                client.sendObject(new Message(0, clientName, "login"));**/
                lobbyView = new LobbyView(primaryStage, localisator);
                lobbyModel = new LobbyModel();
                lobbyController = new LobbyController(lobbyModel, lobbyView, localisator);
            //}
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
            loginView.switchBox.setPromptText(localisator.getResourceBundle().getString("language"));
            loginView.userNameValid.setText(localisator.getResourceBundle().getString("validUsername"));
        }

}



