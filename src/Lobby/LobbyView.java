package Lobby;

import Client_Server.Chat.ChatWindow;
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
    public Label paricipateLabel, gameLabel, userLabel1, userLabel2;
    protected Localisator localisator;
    protected ChatWindow chatWindow;

    public LobbyView(Stage primaryStage, Localisator localisator) {
        this.primaryStage = primaryStage;
        this.localisator = localisator;
        chatWindow = new ChatWindow(localisator);

        paricipateLabel = new Label(localisator.getResourceBundle().getString("participate"));
        gameLabel = new Label(localisator.getResourceBundle().getString("game"));
        userLabel1 = new Label("Spieler1");
        userLabel2 = new Label("Spieler2");
        startButton = new Button(localisator.getResourceBundle().getString("ready"));

        gameLabel.getStyleClass().add("ipLabel");
        userLabel1.getStyleClass().add("userLabel");
        userLabel2.getStyleClass().add("userLabel");
        paricipateLabel.getStyleClass().add("userLabel");
        
        HBox topBox = new HBox();
        topBox.setPadding(new Insets(100, 0, 0, 80));
        topBox.getChildren().addAll(gameLabel);
        HBox botBox = new HBox();
        botBox.setPadding(new Insets(0, 0, 120, 80));
        botBox.getChildren().addAll(startButton);
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        root.setLeft(gridPane);
        root.setTop(topBox);
        root.setBottom(botBox);
        root.setRight(chatWindow.getRoot());
        gridPane.add(paricipateLabel,0,0);
        gridPane.add(userLabel1,0,1);
        gridPane.add(userLabel2,0,2);
        gridPane.setPadding(new Insets(100, 0, 0, 80));
        gridPane.setHgap(40);
        gridPane.setVgap(40);

        //Scene Initialisieren
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../Stylesheets/LobbyStyles.css").toExternalForm());

    }

    public void start() {
        primaryStage.show();
    }

    public void stop() {
        primaryStage.hide();
    }
}

