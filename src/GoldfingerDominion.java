import Lobby.LobbyView;
import Login.LoginController;
import Login.LoginModel;
import Login.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 30.09.2017.
 */
public class GoldfingerDominion extends Application {
    private static LoginModel loginModel;
    private static LoginView loginView;
    private static LoginController loginController;
    private static Stage primaryStage;



    public static void main(String[] args) {

        launch();

    }


    @Override
    public void start(Stage primaryStage) {
        GoldfingerDominion.primaryStage = primaryStage;


        loginModel = new LoginModel();
        loginView = new LoginView(primaryStage);
        loginController = new LoginController(loginModel, loginView, primaryStage);


        loginView.start();


    }



    @Override
    public void stop() {
        if (loginView != null) loginView.stop();
    }




}




