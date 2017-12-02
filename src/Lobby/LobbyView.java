package Lobby;

import Client_Server.Chat.ChatWindow;
import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class LobbyView {

    public Stage primaryStage;
    public Button startButton;
    protected Button spectatorButton;
    public Label participateLabel, gameLabel, userLabel1, userLabel2, userLabel3, userLabel4;
    protected Localisator localisator;
    protected ChatWindow chatWindow;
    protected ArrayList<Label> players = new ArrayList<>();

    public LobbyView(Stage primaryStage, Localisator localisator) {
        this.primaryStage = primaryStage;
        this.localisator = localisator;
        chatWindow = new ChatWindow(localisator);
        chatWindow.getVBox().setPadding(new Insets(70, 90, 80, 0));

        participateLabel = new Label(localisator.getResourceBundle().getString("participate"));
        gameLabel = new Label(localisator.getResourceBundle().getString("game"));
        userLabel1 = new Label("unknown");
        userLabel2 = new Label("unknown");
        userLabel3 = new Label("unknown");
        userLabel4 = new Label("unknown");
        startButton = new Button(localisator.getResourceBundle().getString("start"));
        spectatorButton = new Button(localisator.getResourceBundle().getString("spectator"));
        players.add(userLabel1);
        players.add(userLabel2);
        players.add(userLabel3);
        players.add(userLabel4);
        for (Label label : players){
            label.getStyleClass().add("userLabel");
            label.setVisible(false);
        }

        gameLabel.getStyleClass().add("ipLabel");
        participateLabel.getStyleClass().add("participateLabel");

        HBox topBox = new HBox();
        topBox.setPadding(new Insets(50, 0, 0, 80));
        topBox.getChildren().addAll(gameLabel);
        HBox botBox = new HBox();
        botBox.setPadding(new Insets(0, 0, 120, 80));
        botBox.setSpacing(430);
        botBox.getChildren().addAll(startButton, spectatorButton);
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        root.setLeft(gridPane);
        root.setTop(topBox);
        root.setBottom(botBox);
        root.setRight(chatWindow.getRoot());

        gridPane.add(participateLabel,0,0);
        gridPane.add(userLabel1,0,1);
        gridPane.add(userLabel2,0,2);
        gridPane.add(userLabel3,0,3);
        gridPane.add(userLabel4,0,4);
        gridPane.setPadding(new Insets(70, 0, 0, 80));
        gridPane.setHgap(40);
        gridPane.setVgap(40);

        //Scene Initialisieren
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../Stylesheets/LobbyStyles.css").toExternalForm());

    }

    public ChatWindow getChatWindow() {
        return chatWindow;
    }

    public ArrayList<Label> getPlayers() {
        return players;
    }

    public void start() {
        primaryStage.show();
    }

    public void stop() {
        primaryStage.hide();
    }
}

