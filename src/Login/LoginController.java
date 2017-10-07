package Login;


import Lobby.LobbyController;
import Lobby.LobbyModel;
import Lobby.LobbyView;
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


    public LoginController(LoginModel loginModel, LoginView loginView, Stage primaryStage) {
        this.loginModel = loginModel;
        this.loginView = loginView;
        this.primaryStage = primaryStage;



        loginView.choinButton.setOnAction(event -> {

            lobbyView = new LobbyView(primaryStage);
            lobbyModel = new LobbyModel();
            lobbyController = new LobbyController(lobbyModel, lobbyView);

        });

        loginView.hostButton.setOnAction(event -> {

            lobbyView = new LobbyView(primaryStage);
            lobbyModel = new LobbyModel();
            lobbyController = new LobbyController(lobbyModel, lobbyView);

        });

    }
}



