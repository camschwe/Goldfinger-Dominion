package Login;

import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class LoginView {

    protected Stage primaryStage;
    protected Button hostButton, joinButton;
    protected TextField userNameField;
    protected Label userNameLabel;
    protected ComboBox<String> switchBox;
    protected Localisator localisator;

    public LoginView(Stage primaryStage, Localisator localisator) {
        this.primaryStage = primaryStage;
        this.localisator = localisator;

        userNameLabel = new Label(localisator.getResourceBundle().getString("username"));
        hostButton = new Button(localisator.getResourceBundle().getString("hosting"));
        joinButton = new Button(localisator.getResourceBundle().getString("join"));
        userNameField = new TextField();
        userNameField.setPromptText(localisator.getResourceBundle().getString("username"));

        switchBox = new ComboBox<String>();
        switchBox.setPromptText(localisator.getResourceBundle().getString("language"));
        switchBox.getItems().addAll(
                "English",
                "Deutsch",
                "Schwiizerdütsch"
        );

        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        root.setCenter(gridPane);
        gridPane.add(userNameLabel, 0, 0);
        gridPane.add(userNameField, 1, 0);
        gridPane.add(hostButton, 0, 1);
        gridPane.add(joinButton, 1, 1);
        gridPane.setPadding(new Insets(400, 0, 0, 280));
        gridPane.setHgap(40);
        gridPane.setVgap(40);
        HBox hBox = new HBox();
        root.setBottom(hBox);
        hBox.setPadding(new Insets(0, 0, 40, 740));
        hBox.getChildren().addAll(switchBox);

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

