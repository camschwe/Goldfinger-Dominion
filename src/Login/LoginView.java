package Login;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class LoginView {

    private Stage stage;
    private Label helloLabel;


    public LoginView(Stage stage, LoginModel model) {
        this.stage = stage;


        //Initialisierung der Label
        helloLabel = new Label("Hallo Welt");

        BorderPane root = new BorderPane();
        root.setCenter(helloLabel);

        //Scene Initialisieren
        Scene scene = new Scene(root, 1400, 800);
        stage.setScene(scene);
        stage.setTitle("Goldfinger Dominion");

    }

    public void start() {
        stage.show();
    }

    public void stop() {
        stage.hide();
    }

}

