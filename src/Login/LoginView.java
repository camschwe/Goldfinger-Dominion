package Login;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class LoginView {

    protected Stage primaryStage;
    protected Button hostButton, choinButton;
    protected TextField userNameField;
    protected Label userNameLabel;


    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;


        userNameLabel = new Label("Benutzername: ");
        hostButton = new Button("Server hosten");
        choinButton = new Button("Server beitreten");
        userNameField = new TextField();
        userNameField.setPromptText("Benutzername");

        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        root.setCenter(gridPane);
        gridPane.add(userNameLabel, 0, 0);
        gridPane.add(userNameField, 1, 0);
        gridPane.add(hostButton, 0, 1);
        gridPane.add(choinButton, 1, 1);
        gridPane.setPadding(new Insets(400, 0, 0, 280));
        gridPane.setHgap(40);
        gridPane.setVgap(40);


        //Scene Initialisieren
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("LoginStyles.css").toExternalForm());
        primaryStage.setTitle("Goldfinger Dominion");
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(800);
        primaryStage.setMinWidth(100);
        primaryStage.setMinHeight(800);

    }

    public void start() {
        primaryStage.show();
    }

    public void stop() {
        primaryStage.hide();
    }


}

