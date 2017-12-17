package Lobby;

import Client_Server.Chat.ChatWindow;
import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
 *
 * View für die Lobby - Mit label für die maximale Anzahl von Spielern, welche nach dem Beitritt sichtbar werden und
 * mit dem entsprechenden Namen ergänzt sind. Zudem ist die ChatView implementiert. Es hat einen Button für den Host
 * zum Spielstart und zudem noch ein Button um einem Spiel zuzuschauen.
 */
public class LobbyView {

    public Stage primaryStage;
    protected Button startButton, spectatorButton, musicButton;
    public Label participateLabel, gameLabel, userLabel1, userLabel2, userLabel3, userLabel4, addressLabel;
    protected Localisator localisator;
    protected ChatWindow chatWindow;
    protected ArrayList<Label> players = new ArrayList<>();
    protected CheckBox roundLimit;
    protected ComboBox<String> round;

    public LobbyView(Stage primaryStage, Localisator localisator) {
        this.primaryStage = primaryStage;
        this.localisator = localisator;

        /**
         * ChatWindow
         */

        chatWindow = new ChatWindow(localisator);
        chatWindow.getVBox().setPadding(new Insets(70, 90, 80, 0));

        /**
         * Label und Buttons
         */
        participateLabel = new Label(localisator.getResourceBundle().getString("participate"));
        gameLabel = new Label(localisator.getResourceBundle().getString("game"));
        addressLabel = new Label("");
        addressLabel.setVisible(false);
        addressLabel.getStyleClass().add("addressLabel");
        roundLimit = new CheckBox(localisator.getResourceBundle().getString("roundLimit"));
        roundLimit.setVisible(false);
        roundLimit.getStyleClass().add("roundLimit");
        round = new ComboBox<>();
        for (int i = 0; i <= 20; i++){
            int j = i + 15;
            round.getItems().add("" + j);
        }
        round.getSelectionModel().selectLast();
        round.setVisible(false);
        userLabel1 = new Label("unknown");
        userLabel2 = new Label("unknown");
        userLabel3 = new Label("unknown");
        userLabel4 = new Label("unknown");
        startButton = new Button(localisator.getResourceBundle().getString("start"));
        spectatorButton = new Button(localisator.getResourceBundle().getString("spectator"));
        musicButton = new Button();
        VBox musicBox = new VBox();
        musicBox.getChildren().add(musicButton);
        musicBox.setPadding(new Insets(10));
        musicBox.setAlignment(Pos.TOP_RIGHT);
        gameLabel.getStyleClass().add("ipLabel");
        participateLabel.getStyleClass().add("participateLabel");

        players.add(userLabel1);
        players.add(userLabel2);
        players.add(userLabel3);
        players.add(userLabel4);
        for (Label label : players){
            label.getStyleClass().add("userLabel");
            label.setVisible(false);
        }

        /**
         * Container
         */

        HBox topBox = new HBox();
        VBox leftBox = new VBox();
        HBox playerBox = new HBox();
        topBox.setPadding(new Insets(50, 0, 0, 80));
        topBox.getChildren().addAll(gameLabel, musicBox);
        HBox botBox = new HBox();
        botBox.setPadding(new Insets(0, 0, 120, 80));
        botBox.setSpacing(430);
        botBox.getChildren().addAll(startButton, spectatorButton);
        HBox rounds = new HBox();
        rounds.getChildren().addAll(roundLimit, round);
        rounds.setPadding(new Insets(25, 5, 0, 0));
        rounds.setSpacing(10);
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        root.setLeft(leftBox);
        root.setTop(topBox);
        root.setBottom(botBox);
        root.setRight(chatWindow.getRoot());
        leftBox.getChildren().addAll(addressLabel, playerBox);
        leftBox.setPadding(new Insets(30, 0, 0, 80));
        playerBox.getChildren().addAll(gridPane, rounds);

        gridPane.add(participateLabel,0,0);
        gridPane.add(userLabel1,0,1);
        gridPane.add(userLabel2,0,2);
        gridPane.add(userLabel3,0,3);
        gridPane.add(userLabel4,0,4);
        gridPane.setPadding(new Insets(10, 30, 0, 0));
        gridPane.setHgap(40);
        gridPane.setVgap(40);

        /**
         * Initialisierung der Scene mit der breits definierten Stage aus der LoginView
         */

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

