package Lobby;

import Localisation.Localisator;
import Login.LoginView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
public class LobbyView {

    public Stage primaryStage;
    public Button startButton;
    public TextField userField;
    public Label ipLabel, userLabel1, userLabel2;
    protected Localisator localisator;

    public LobbyView(Stage primaryStage, Localisator localisator) {
        this.primaryStage = primaryStage;
        this.localisator = localisator;

        ipLabel = new Label(localisator.getResourceBundle().getString("IP")+": 46.127.72.195");
        userLabel1 = new Label("Spieler1");
        userLabel2 = new Label("Spieler2");
        startButton = new Button(localisator.getResourceBundle().getString("ready"));

        ipLabel.getStyleClass().add("ipLabel");
        userLabel1.getStyleClass().add("userLabel");
        userLabel2.getStyleClass().add("userLabel");

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(50, 0, 0, 50));
        hBox.getChildren().addAll(ipLabel);
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        root.setLeft(gridPane);
        root.setTop(hBox);
        gridPane.add(userLabel1,0,0);
        gridPane.add(userLabel2,0,1);
        gridPane.add(startButton, 0, 2);
        gridPane.setPadding(new Insets(100, 0, 0, 50));
        gridPane.setHgap(40);
        gridPane.setVgap(40);

        //Scene Initialisieren
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../Stylesheets/LobbyStyles.css").toExternalForm());
        //entryStage.setTitle("Goldfinger Dominion");
    }

    public void start() {
        primaryStage.show();
    }

    public void stop() {
        primaryStage.hide();
    }
}

