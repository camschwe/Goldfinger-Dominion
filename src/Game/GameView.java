package Game;

import Client_Server.Chat.ChatWindow;
import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class GameView {

    protected Stage gameStage;

    /**
     * View für das Game mit der GameStage in 1080p - skalierbarkeit geplant auf andere Seitenverhältnisse
     */
    private Localisator localisator;
    private ChatWindow chatWindow;
    public Button resourceButton, actionButton, phaseButton,putStapelPlayer1;
    public HBox player1Box, player2Box;
    public Label moneyLabel1, moneyLabel2, phaseLabel1, phaseLabel2, pointLabel1, pointLabel2;
    public GridPane resourcePane, actionPane;


    public GameView(Stage gameStage, Localisator localisator, ChatWindow chatWindow) {
        this.gameStage = gameStage;
        this.localisator = localisator;
        this.chatWindow = chatWindow;


        /**
         * Initialisierung der Container
         */

        BorderPane root = new BorderPane();
        BorderPane midPane = new BorderPane();
        BorderPane resourceBorder = new BorderPane();
        BorderPane rightMainPane = new BorderPane();
        BorderPane rightPane = new BorderPane();
        resourcePane = new GridPane();
        BorderPane leftPane = new BorderPane();
        BorderPane actionBorder = new BorderPane();
        GridPane player1Pane = new GridPane();
        GridPane player2Pane = new GridPane();
        actionPane = new GridPane();
        BorderPane chatPane = new BorderPane();
        player1Box = new HBox();
        player2Box = new HBox();
        HBox player2CardBox = new HBox();
        HBox player1CardBox = new HBox();



        /**
         * Verteilung der Container
         */


        root.setCenter(midPane);
        root.setLeft(leftPane);
        root.setRight(rightMainPane);
        rightMainPane.setCenter(rightPane);
        leftPane.setTop(resourceBorder);
        leftPane.setBottom(player1CardBox);
        leftPane.setCenter(player1Pane);
        midPane.setCenter(actionBorder);
        midPane.setTop(player2Box);
        midPane.setBottom(player1Box);
        rightPane.setCenter(player2Pane);
        rightPane.setTop(player2CardBox);
        rightMainPane.setRight(chatPane);
        resourceBorder.setCenter(resourcePane);
        actionBorder.setCenter(actionPane);




        /**
         * Pane mit Ressourcenkarten
         */

        resourceButton = new Button();
        resourceButton.getStyleClass().add("bigButton");
        resourceButton.getStyleClass().add("invisible");

        resourceBorder.setRight(resourceButton);
        resourcePane.setHgap(20);
        resourcePane.setVgap(20);
        resourcePane.setPadding(new Insets(20, 20, 0, 20));

        /**
         * Chat, TextArea und PhaseButton
         */

        TextArea noteArea = new TextArea ();
        noteArea.setPromptText("note");
        noteArea.setEditable(false);
        noteArea.getStyleClass().add("noteArea");
        chatPane.setTop(noteArea);
        chatPane.setBottom(chatWindow.getRoot());

        phaseButton = new Button(localisator.getResourceBundle().getString("endPhase"));
        phaseButton.getStyleClass().add("klickButton");
        rightMainPane.setBottom(phaseButton);

        /**
         * Label und Buttons Spieler 1
         */

        Label playerLabel1 = new Label ( "Spieler 1");
        playerLabel1.getStyleClass().add("nameLabel");
        moneyLabel1 = new Label(localisator.getResourceBundle().getString("money")+ ":\t0");
        pointLabel1 = new Label(localisator.getResourceBundle().getString("point")+ ":\t0");
        phaseLabel1 = new Label(localisator.getResourceBundle().getString("phase")+
                ":\t" +localisator.getResourceBundle().getString( "buy"));
        moneyLabel1.getStyleClass().add("resourceLabel");
        pointLabel1.getStyleClass().add("resourceLabel");
        phaseLabel1.getStyleClass().add("resourceLabel");
        putStapelPlayer1 = new Button();
        putStapelPlayer1.getStyleClass().add("mediumButton");
        putStapelPlayer1.getStyleClass().add("trash");
        Button drawStapelPlayer1 = new Button();
        drawStapelPlayer1.getStyleClass().add("mediumButton");
        drawStapelPlayer1.getStyleClass().add("back1");

        player1Pane.add(playerLabel1, 0,0);
        player1Pane.add(moneyLabel1, 0,1);
        player1Pane.add(pointLabel1, 0,2);
        player1Pane.add(phaseLabel1, 0,3);
        player1Pane.setPadding(new Insets(150,0,0,20));
        player1Pane.setVgap(5);
        player1CardBox.getChildren().addAll(putStapelPlayer1, drawStapelPlayer1);
        player1CardBox.setSpacing(10);
        player1CardBox.setPadding(new Insets(0,0,10,20));

        player1Box.setPadding((new Insets(20, 20, 20, 20)));
        player1Box.setSpacing(20);
        player1Box.setAlignment(Pos.BOTTOM_CENTER);


        /**
         * Label und Buttons Spieler 2
         */

        Label playerLabel2 = new Label ("Spieler 2");
        playerLabel2.getStyleClass().add("nameLabel");
        moneyLabel2 = new Label(localisator.getResourceBundle().getString("money")+ ":\t0");
        pointLabel2 = new Label(localisator.getResourceBundle().getString("point")+ ":\t0");
        phaseLabel2 = new Label(localisator.getResourceBundle().getString("phase")+
                ":\t" +localisator.getResourceBundle().getString( "buy"));
        moneyLabel2.getStyleClass().add("resourceLabel");
        pointLabel2.getStyleClass().add("resourceLabel");
        phaseLabel2.getStyleClass().add("resourceLabel");
        Button putStapelPlayer2 = new Button();
        putStapelPlayer2.getStyleClass().add("mediumButton");
        putStapelPlayer2.getStyleClass().add("trash");
        Button drawStapelPlayer2 = new Button();
        drawStapelPlayer2.getStyleClass().add("mediumButton");
        drawStapelPlayer2.getStyleClass().add("back2");

        player2Pane.add(playerLabel2, 0,1);
        player2Pane.add(moneyLabel2, 0,2);
        player2Pane.add(pointLabel2, 0,3);
        player2Pane.add(phaseLabel2, 0,4);
        player2Pane.setPadding(new Insets(200,0,0,0));
        player2Pane.setVgap(5);
        player2CardBox.getChildren().addAll(putStapelPlayer2, drawStapelPlayer2);
        player2CardBox.setSpacing(10);
        player2CardBox.setPadding(new Insets(10,0,0,0));

        //TODO: mit Array schöner gestalten
        for(int i = 0; i<5; i++){
            Button player2Card = new Button();
            player2Card.getStyleClass().add("mediumButton");
            player2Card.getStyleClass().add("back2");
            player2Box.getChildren().add(player2Card);
        }

        player2Box.setPadding((new Insets(20, 20, 20, 20)));
        player2Box.setSpacing(20);


        /**
         * Pane mit Aktionskarten
         */

        //TODO: mit Array schöner gestalten
        actionButton = new Button();
        actionButton.getStyleClass().add("bigButton");
        actionButton.getStyleClass().add("invisible");
        actionBorder.setLeft(actionButton);

        actionPane.setHgap(20);
        actionPane.setVgap(20);
        actionPane.setPadding(new Insets(20, 20, 0, 0));
        actionPane.setAlignment(Pos.TOP_CENTER);

        /**
         * Scene
         */

        //Scene Initialisieren
        Scene scene = new Scene(root, 1920, 1080);
        gameStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../Stylesheets/GameStyles.css").toExternalForm());
        gameStage.setTitle("Goldfinger Dominion");
        gameStage.setFullScreen(true);

    }

    public void start() {
        gameStage.show();
    }

    public void stop() {
        gameStage.hide();
    }


}

