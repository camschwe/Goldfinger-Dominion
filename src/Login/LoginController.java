package Login;


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


    public LoginController(LoginModel loginModel, LoginView loginView, Stage primaryStage, Localisator localisator) {
        this.loginModel = loginModel;
        this.loginView = loginView;
        this.primaryStage = primaryStage;
        this.localisator = localisator;


        loginView.joinButton.setOnAction(event -> {

            lobbyView = new LobbyView(primaryStage, localisator);
            lobbyModel = new LobbyModel();
            lobbyController = new LobbyController(lobbyModel, lobbyView);

        });

        loginView.hostButton.setOnAction(event -> {

            lobbyView = new LobbyView(primaryStage, localisator);
            lobbyModel = new LobbyModel();
            lobbyController = new LobbyController(lobbyModel, lobbyView);

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

        }












}



