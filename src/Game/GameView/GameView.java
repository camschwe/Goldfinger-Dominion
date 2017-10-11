package Game.GameView;

import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    public Button copperButton, silverButton, goldButton, estateButton, duchyButton, provinceButton,resourceButton;
    public Button actionButton, actionButton1, actionButton2, actionButton3, actionButton4, actionButton5, actionButton6;
    public Button actionButton7, actionButton8, actionButton9,actionButton10;


    public GameView(Stage gameStage, Localisator localisator) {
        this.gameStage = gameStage;
        this.localisator = localisator;



        BorderPane root = new BorderPane();
        BorderPane midPane = new BorderPane();
        BorderPane resourceBorder = new BorderPane();
        BorderPane rightPane = new BorderPane();
        GridPane resourePane = new GridPane();
        BorderPane leftPane = new BorderPane();
        GridPane player1Pane = new GridPane();
        GridPane player2Pane = new GridPane();
        GridPane notePane = new GridPane();
        GridPane actionPane = new GridPane();
        HBox player1Box = new HBox();
        HBox player2Box = new HBox();

        root.setCenter(midPane);
        root.setLeft(resourceBorder);
        root.setRight(rightPane);
        resourceBorder.setTop(leftPane);
        leftPane.setLeft(resourePane);
        resourceBorder.setBottom(player1Pane);
        midPane.setCenter(actionPane);
        rightPane.setLeft(player2Pane);
        midPane.setTop(player2Box);
        midPane.setBottom(player1Box);

        estateButton = new Button();
        estateButton.setId("estateButton");
        duchyButton = new Button();
        duchyButton.setId("duchyButton");
        provinceButton = new Button();
        provinceButton.setId("provinceButton");
        copperButton = new Button();
        copperButton.setId("copperButton");
        silverButton = new Button();
        silverButton.setId("silverButton");
        goldButton = new Button();
        goldButton.setId("goldButton");
        resourceButton = new Button();
        resourceButton.getStyleClass().add("invisible");


        leftPane.setCenter(resourceButton);
        resourePane.add(provinceButton, 0,0);
        resourePane.add(duchyButton, 0,1);
        resourePane.add(estateButton, 0,2);
        resourePane.add(goldButton, 1,0);
        resourePane.add(silverButton, 1,1);
        resourePane.add(copperButton, 1,2);
        resourePane.setHgap(30);
        resourePane.setVgap(30);

        Label moneyLabel1 = new Label(localisator.getResourceBundle().getString("money")+ ":\t2");
        Label pointLabel1 = new Label(localisator.getResourceBundle().getString("point")+ ":\t2");
        Label phaseLabel1 = new Label(localisator.getResourceBundle().getString("phase")+ ":\tAction");
        Button putStapelPlayer1 = new Button();
        putStapelPlayer1.getStyleClass().add("village");
        Button drawStapelPlayer1 = new Button();
        drawStapelPlayer1.getStyleClass().add("back1");

        player1Pane.add(moneyLabel1, 0,0);
        player1Pane.add(pointLabel1, 0,1);
        player1Pane.add(phaseLabel1, 0,2);
        player1Pane.add(putStapelPlayer1, 0,3);
        player1Pane.add(drawStapelPlayer1, 1,3);

        Label moneyLabel2 = new Label(localisator.getResourceBundle().getString("money")+ ":\t2");
        Label pointLabel2 = new Label(localisator.getResourceBundle().getString("point")+ ":\t2");
        Label phaseLabel2 = new Label(localisator.getResourceBundle().getString("phase")+ ":\tAction");
        Button putStapelPlayer2 = new Button();
        putStapelPlayer2.getStyleClass().add("back2");
        Button drawStapelPlayer2 = new Button();
        drawStapelPlayer2.getStyleClass().add("village");

        player2Pane.add(moneyLabel2, 0,1);
        player2Pane.add(pointLabel2, 0,2);
        player2Pane.add(phaseLabel2, 0,3);
        player2Pane.add(putStapelPlayer2, 0,0);
        player2Pane.add(drawStapelPlayer2, 1,0);


        //TODO: mit Array schöner gestalten
        actionButton = new Button();
        actionButton.getStyleClass().add("invisible");
        midPane.setRight(actionButton);
        actionButton1 = new Button();
        actionButton1.getStyleClass().add("villageButton");
        actionPane.add(actionButton1, 0, 0);
        actionButton2 = new Button();
        actionButton2.getStyleClass().add("villageButton");
        actionPane.add(actionButton2, 1, 0);
        actionButton3 = new Button();
        actionButton3.getStyleClass().add("villageButton");
        actionPane.add(actionButton3, 2, 0);
        actionButton4 = new Button();
        actionButton4.getStyleClass().add("villageButton");
        actionPane.add(actionButton4, 3, 0);
        actionButton5 = new Button();
        actionButton5.getStyleClass().add("villageButton");
        actionPane.add(actionButton5, 4, 0);
        actionButton6 = new Button();
        actionButton6.getStyleClass().add("villageButton");
        actionPane.add(actionButton6, 0, 1);
        actionButton7 = new Button();
        actionButton7.getStyleClass().add("villageButton");
        actionPane.add(actionButton7, 1, 1);
        actionButton8 = new Button();
        actionButton8.getStyleClass().add("villageButton");
        actionPane.add(actionButton8, 2, 1);
        actionButton9 = new Button();
        actionButton9.getStyleClass().add("villageButton");
        actionPane.add(actionButton9, 3, 1);
        actionButton10 = new Button();
        actionButton10.getStyleClass().add("villageButton");
        actionPane.add(actionButton10, 4, 1);
        actionPane.setHgap(30);
        actionPane.setVgap(30);




        //TODO: mit Array schöner gestalten
        for(int i = 0; i<5; i++){
            Button player1Card = new Button();
            player1Card.getStyleClass().add("back1");
            player1Box.getChildren().add(player1Card);
        }

        //TODO: mit Array schöner gestalten
        for(int i = 0; i<5; i++){
            Button player2Card = new Button();
            player2Card.getStyleClass().add("back2");
            player2Box.getChildren().add(player2Card);
        }

        Label noteLabel = new Label ("Hinweis:\n\nnimm endlich diese verdammte Karte\n\n\nendlich bist du so weit!");
        rightPane.setBottom(noteLabel);





        //Scene Initialisieren
        Scene scene = new Scene(root, 1800, 1000);
        gameStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("GameStyles.css").toExternalForm());
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

