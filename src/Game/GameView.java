package Game;

import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class GameView {

    protected Stage gameStage;
    private Localisator localisator;
    public Button resourceButton;
    public Button actionButton, actionButton1, actionButton2, actionButton3, actionButton4, actionButton5, actionButton6;
    public Button actionButton7, actionButton8, actionButton9,actionButton10;
    public HBox player1Box, player2Box;
    public Label moneyLabel1, moneyLabel2, phaseLabel1, phaseLabel2, pointLabel1, pointLabel2;
    public GridPane resourcePane;


    public GameView(Stage gameStage, Localisator localisator) {
        this.gameStage = gameStage;
        this.localisator = localisator;



        BorderPane root = new BorderPane();
        BorderPane mainPane = new BorderPane();
        BorderPane midPane = new BorderPane();
        BorderPane resourceBorder = new BorderPane();
        BorderPane rightPane = new BorderPane();
        resourcePane = new GridPane();
        BorderPane leftPane = new BorderPane();
        BorderPane actionBorder = new BorderPane();
        GridPane player1Pane = new GridPane();
        GridPane player2Pane = new GridPane();
        GridPane actionPane = new GridPane();
        GridPane chatPane = new GridPane();
        player1Box = new HBox();
        player2Box = new HBox();
        HBox player2CardBox = new HBox();
        HBox player1CardBox = new HBox();

        root.setCenter(midPane);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        leftPane.setTop(resourceBorder);
        leftPane.setBottom(player1CardBox);
        leftPane.setCenter(player1Pane);
        midPane.setCenter(actionBorder);
        rightPane.setCenter(player2Pane);
        rightPane.setTop(player2CardBox);
        rightPane.setRight(chatPane);
        midPane.setTop(player2Box);
        midPane.setBottom(player1Box);
        resourceBorder.setCenter(resourcePane);
        actionBorder.setCenter(actionPane);

        resourceButton = new Button();
        resourceButton.getStyleClass().add("copperBig");
        resourceButton.getStyleClass().add("invisible");


        resourceBorder.setRight(resourceButton);


        resourcePane.setHgap(20);
        resourcePane.setVgap(20);
        resourcePane.setPadding(new Insets(20, 20, 0, 20));

        Label playerLabel1 = new Label ( "Spieler 1");
        playerLabel1.getStyleClass().add("nameLabel");
        moneyLabel1 = new Label(localisator.getResourceBundle().getString("money")+ ":\t0");
        pointLabel1 = new Label(localisator.getResourceBundle().getString("point")+ ":\t0");
        phaseLabel1 = new Label(localisator.getResourceBundle().getString("phase")+ ":\tAction");
        moneyLabel1.getStyleClass().add("resourceLabel");
        pointLabel1.getStyleClass().add("resourceLabel");
        phaseLabel1.getStyleClass().add("resourceLabel");
        Button putStapelPlayer1 = new Button();
        putStapelPlayer1.getStyleClass().add("village");
        Button drawStapelPlayer1 = new Button();
        drawStapelPlayer1.getStyleClass().add("back1");

        player1Pane.add(playerLabel1, 0,0);
        player1Pane.add(moneyLabel1, 0,1);
        player1Pane.add(pointLabel1, 0,2);
        player1Pane.add(phaseLabel1, 0,3);
        player1CardBox.getChildren().addAll(putStapelPlayer1, drawStapelPlayer1);

        Label playerLabel2 = new Label ("Spieler 2");
        playerLabel2.getStyleClass().add("nameLabel");
        moneyLabel2 = new Label(localisator.getResourceBundle().getString("money")+ ":\t0");
        pointLabel2 = new Label(localisator.getResourceBundle().getString("point")+ ":\t0");
        phaseLabel2 = new Label(localisator.getResourceBundle().getString("phase")+ ":\tAction");
        moneyLabel2.getStyleClass().add("resourceLabel");
        pointLabel2.getStyleClass().add("resourceLabel");
        phaseLabel2.getStyleClass().add("resourceLabel");
        Button putStapelPlayer2 = new Button();
        putStapelPlayer2.getStyleClass().add("back2");
        Button drawStapelPlayer2 = new Button();
        drawStapelPlayer2.getStyleClass().add("village");

        player2Pane.add(playerLabel2, 0,1);
        player2Pane.add(moneyLabel2, 0,2);
        player2Pane.add(pointLabel2, 0,3);
        player2Pane.add(phaseLabel2, 0,4);
        player2CardBox.getChildren().addAll(drawStapelPlayer2, putStapelPlayer2);


        //TODO: mit Array schöner gestalten
        actionButton = new Button();
        actionButton.getStyleClass().add("invisible");
        actionBorder.setBottom(actionButton);
        actionButton1 = new Button();
        actionButton1.getStyleClass().add("villageSmall");
        actionPane.add(actionButton1, 0, 0);
        actionButton2 = new Button();
        actionButton2.getStyleClass().add("villageSmall");
        actionPane.add(actionButton2, 1, 0);
        actionButton3 = new Button();
        actionButton3.getStyleClass().add("villageSmall");
        actionPane.add(actionButton3, 2, 0);
        actionButton4 = new Button();
        actionButton4.getStyleClass().add("villageSmall");
        actionPane.add(actionButton4, 3, 0);
        actionButton5 = new Button();
        actionButton5.getStyleClass().add("villageSmall");
        actionPane.add(actionButton5, 4, 0);
        actionButton6 = new Button();
        actionButton6.getStyleClass().add("villageSmall");
        actionPane.add(actionButton6, 0, 1);
        actionButton7 = new Button();
        actionButton7.getStyleClass().add("villageSmall");
        actionPane.add(actionButton7, 1, 1);
        actionButton8 = new Button();
        actionButton8.getStyleClass().add("villageSmall");
        actionPane.add(actionButton8, 2, 1);
        actionButton9 = new Button();
        actionButton9.getStyleClass().add("villageSmall");
        actionPane.add(actionButton9, 3, 1);
        actionButton10 = new Button();
        actionButton10.getStyleClass().add("villageSmall");
        actionPane.add(actionButton10, 4, 1);
        actionPane.setHgap(20);
        actionPane.setVgap(20);
        actionPane.setPadding(new Insets(20, 20, 0, 0));



        player1Box.setPadding((new Insets(20, 20, 20, 20)));
        player1Box.setSpacing(20);

        //TODO: mit Array schöner gestalten
        for(int i = 0; i<5; i++){
            Button player2Card = new Button();
            player2Card.getStyleClass().add("back2");
            player2Box.getChildren().add(player2Card);
        }

        player2Box.setPadding((new Insets(20, 20, 20, 20)));
        player2Box.setSpacing(20);

        TextArea noteArea = new TextArea ();
        noteArea.setPromptText("note");
        noteArea.setEditable(false);
        noteArea.getStyleClass().add("noteArea");
        chatPane.add(noteArea, 0,0);

        TextArea chatArea = new TextArea ();
        chatArea.setPromptText("Chat");
        chatArea.setEditable(false);
        chatArea.getStyleClass().add("chatArea");
        chatPane.add(chatArea,0,1);




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

