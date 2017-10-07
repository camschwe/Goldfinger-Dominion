import Login.LoginController;
import Login.LoginModel;
import Login.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 30.09.2017.
 */
public class GoldfingerDominion extends Application {
    private static LoginModel model;
    private static LoginView view;
    private static LoginController controller;


    public static void main(String[] args) {

        launch();

    }

    @Override
    public void start(Stage primaryStage) {
        model = new LoginModel();
        view = new LoginView(primaryStage, model);
        controller = new LoginController();

        view.start();


    }

    @Override
    public void stop() {
        if (view != null) view.stop();
    }


}




