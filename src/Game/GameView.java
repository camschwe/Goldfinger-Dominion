package Game;

import Client_Server.Chat.ChatWindow;
import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
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
    protected ChatWindow chatWindow;
    public Button actionButton, phaseButton, moneyButton, putStapelPlayer1, putStapelPlayer2, drawStapelPlayer2, endGameButton, musicButton;
    public HBox player1Box, player2Box;
    public Label moneyLabel1, moneyLabel2, phaseLabel1, phaseLabel2, pointLabel1, pointLabel2, playerLabel1, playerLabel2, turnLabel, playerLabel;
    public GridPane resourcePane, actionPane;
    protected TextFlow noteFlow;
    public String newLine;
    protected ScrollPane scrollPane;
    protected Scene scene;



    public GameView(Stage gameStage, Localisator localisator, ChatWindow chatWindow, String resolution) {
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
        player1Box = new HBox();
        player2Box = new HBox();
        HBox player2CardBox = new HBox();
        HBox player1CardBox = new HBox();
        VBox chatBox = new VBox();
        VBox musicBox = new VBox();


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
        rightMainPane.setRight(chatBox);
        resourceBorder.setCenter(resourcePane);
        actionBorder.setCenter(actionPane);




        /**
         * Pane mit Ressourcenkarten
         */



        resourcePane.setHgap(20);
        resourcePane.setVgap(20);
        resourcePane.setPadding(new Insets(20, 20, 0, 20));

        if(resolution.equals("720p")){
            resourcePane.setHgap(10);
            resourcePane.setVgap(10);
            resourcePane.setPadding(new Insets(0, 20, 0, 20));
        }


        musicButton = new Button();
        musicBox.getChildren().add(musicButton);
        musicBox.setPadding(new Insets(10));
        musicBox.setAlignment(Pos.TOP_RIGHT);

        /**
         * Chat, TextArea und PhaseButton sowie MoneyButton
         */

        turnLabel = new Label(localisator.getResourceBundle().getString("round")+ ":\t1");
        turnLabel.getStyleClass().add("infoLabel");
        playerLabel = new Label(localisator.getResourceBundle().getString("player")+ ":\tSpieler");
        playerLabel.getStyleClass().add("infoLabel");

        noteFlow = new TextFlow();

        newLine = System.getProperty("line.separator");
        scrollPane = new ScrollPane();
        scrollPane.setContent(noteFlow);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vvalueProperty().bind(noteFlow.heightProperty());
        scrollPane.getStyleClass().add("logWindow");
        chatBox.getChildren().addAll(musicBox, turnLabel, playerLabel, scrollPane, chatWindow.getRoot());
        chatWindow.getVBox().setPadding(new Insets(0, 0, 0, 0));
        chatWindow.getScrollPane().getStyleClass().add("chatWindow");
        chatBox.setPadding(new Insets(100, 5, 0, 20));
        chatBox.setSpacing(40);



        phaseButton = new Button(localisator.getResourceBundle().getString("endPhase"));
        phaseButton.getStyleClass().add("klickButton");


        moneyButton = new Button(localisator.getResourceBundle().getString("playMoneyCards"));
        moneyButton.getStyleClass().add("klickButton");


        VBox buttonBox = new VBox();
        buttonBox.getChildren().addAll(phaseButton, moneyButton);
        buttonBox.setSpacing(10);

        if(resolution.equals("720p")){
            buttonBox.setSpacing(5);
        }

        rightMainPane.setBottom(buttonBox);

        /**
         * Label und Buttons Spieler 1
         */

        playerLabel1 = new Label ( "Spieler 1");
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
        player1Pane.setPadding(new Insets(50,0,0,20));
        player1Pane.setVgap(5);
        player1CardBox.getChildren().addAll(putStapelPlayer1, drawStapelPlayer1);


        if(resolution.equals("720p")){
            player1CardBox.setSpacing(5);
        }else{
            player1CardBox.setSpacing(10);
        }



        player1Box.setAlignment(Pos.BOTTOM_CENTER);

        if(resolution.equals("720p")){
            player1Box.setPadding((new Insets(20, 20, 20, 20)));
            player1Box.setSpacing(5);
        }else{
            player1Box.setPadding((new Insets(20, 20, 20, 20)));
            player1Box.setSpacing(10);
        }

        /**
         * Label und Buttons Spieler 2
         */

        playerLabel2 = new Label ();
        playerLabel2.getStyleClass().addAll("nameLabel");
        moneyLabel2 = new Label(localisator.getResourceBundle().getString("money")+ ":\t0");
        pointLabel2 = new Label(localisator.getResourceBundle().getString("point")+ ":\t3");
        phaseLabel2 = new Label(localisator.getResourceBundle().getString("phase")+
                ":\t" +localisator.getResourceBundle().getString( "buy"));
        moneyLabel2.getStyleClass().add("resourceLabel");
        pointLabel2.getStyleClass().add("resourceLabel");
        phaseLabel2.getStyleClass().add("resourceLabel");
        putStapelPlayer2 = new Button();
        putStapelPlayer2.getStyleClass().addAll("mediumButton", "trash");

        drawStapelPlayer2 = new Button();
        drawStapelPlayer2.getStyleClass().addAll("mediumButton", "back2");


        player2Pane.add(playerLabel2, 0,1);
        player2Pane.add(moneyLabel2, 0,2);
        player2Pane.add(pointLabel2, 0,3);
        player2Pane.add(phaseLabel2, 0,4);
        player2Pane.setPadding(new Insets(200,0,0,0));
        player2Pane.setVgap(5);
        player2CardBox.getChildren().addAll(putStapelPlayer2, drawStapelPlayer2);




        if(resolution.equals("720p")){
            player2CardBox.setSpacing(5);
            player2Box.setPadding((new Insets(10, 0, 100, 0)));
        }else{
            player2CardBox.setSpacing(10);
            player2Box.setPadding((new Insets(20, 20, 20, 80)));
        }


        //TODO: mit Array schöner gestalten
        for(int i = 0; i<5; i++){
            Button player2Card = new Button();
            player2Card.getStyleClass().add("mediumButton");
            player2Card.getStyleClass().add("back2");
            player2Box.getChildren().add(player2Card);


        }



        /**
         * Pane mit Aktionskarten
         */

        //TODO: mit Array schöner gestalten
        actionButton = new Button();
        actionButton.getStyleClass().add("bigButton");
        actionButton.getStyleClass().add("invisible");
        actionBorder.setLeft(actionButton);
        actionBorder.setPadding(new Insets(0,0,0,0));

        actionPane.setHgap(20);
        actionPane.setVgap(20);
        actionPane.setPadding(new Insets(25, 0, 0, 0));
        actionPane.setAlignment(Pos.TOP_CENTER);

        if(resolution.equals("720p")){
            actionPane.setHgap(10);
            actionPane.setVgap(10);
            actionPane.setPadding(new Insets(0, 0, 0, 0));
        }

        /**
         * Scene
         */

        //Scene Initialisieren

        if(resolution.equals("720p")){
            scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("../Stylesheets/GameStylesSmall.css").toExternalForm());
        }else{
            scene = new Scene(root, 1920, 1080);
            scene.getStylesheets().add(getClass().getResource("../Stylesheets/GameStyles.css").toExternalForm());

        }
        gameStage.setScene(scene);

        gameStage.setTitle("Goldfinger Dominion");
        gameStage.getIcons().add(new Image("Backgrounds/DominionSchildTransparent.png"));
        //gameStage.setFullScreen(true);

    }

    public void start() {
        gameStage.show();
    }

    public void stop() {
        gameStage.hide();
    }

    public ChatWindow getChatWindow() {
        return chatWindow;
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void setGameStage(Stage gameStage) {
        this.gameStage = gameStage;
    }

    public Scene getScene(){
        return scene;
    }


}

