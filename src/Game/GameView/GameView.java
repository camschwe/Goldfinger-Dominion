package Game.GameView;

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
    protected Button hostButton, choinButton;
    protected TextField userNameField;
    protected Label userNameLabel;


    public GameView(Stage gameStage) {
        this.gameStage = gameStage;




        BorderPane root = new BorderPane();
        BorderPane midPane = new BorderPane();
        BorderPane leftPane = new BorderPane();
        BorderPane rightPane = new BorderPane();
        GridPane resourePane = new GridPane();
        GridPane player1Pane = new GridPane();
        GridPane player2Pane = new GridPane();
        GridPane notePane = new GridPane();
        GridPane actionPane = new GridPane();
        HBox player1Box = new HBox();
        HBox player2Box = new HBox();

        root.setCenter(midPane);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        leftPane.setTop(resourePane);
        leftPane.setCenter(player1Pane);
        midPane.setCenter(actionPane);
        rightPane.setLeft(player2Pane);
        midPane.setTop(player2Box);
        midPane.setBottom(player1Box);

        Button estateButton = new Button();
        estateButton.setId("estateButton");
        Button duchyButton = new Button();
        duchyButton.setId("duchyButton");
        Button provinceButton = new Button();
        provinceButton.setId("provinceButton");
        Button copperButton = new Button();
        copperButton.setId("copperButton");
        Button silverButton = new Button();
        silverButton.setId("silverButton");
        Button goldButton = new Button();
        goldButton.setId("goldButton");

        resourePane.add(provinceButton, 0,0);
        resourePane.add(duchyButton, 0,1);
        resourePane.add(estateButton, 0,2);
        resourePane.add(goldButton, 1,0);
        resourePane.add(silverButton, 1,1);
        resourePane.add(copperButton, 1,2);

        Label moneyLabel1 = new Label("money"+ ": 2");
        Label pointLabel1 = new Label("point" + ": 2");
        Label phaseLabel1 = new Label("phase" + ": Action");
        Button putStapelPlayer1 = new Button("Ablagestapel");
        Button drawStapelPlayer1 = new Button("Nachziehstapel");

        player1Pane.add(moneyLabel1, 0,0);
        player1Pane.add(pointLabel1, 0,1);
        player1Pane.add(phaseLabel1, 0,2);
        player1Pane.add(putStapelPlayer1, 0,3);
        player1Pane.add(drawStapelPlayer1, 1,3);

        Label moneyLabel2 = new Label("money"+ ": 2");
        Label pointLabel2 = new Label("point" + ": 2");
        Label phaseLabel2 = new Label("phase" + ": Action");
        Button putStapelPlayer2 = new Button("Ablagestapel");
        Button drawStapelPlayer2 = new Button("Nachziehstapel");

        player2Pane.add(moneyLabel2, 0,0);
        player2Pane.add(pointLabel2, 0,1);
        player2Pane.add(phaseLabel2, 0,2);
        player2Pane.add(putStapelPlayer2, 0,3);
        player2Pane.add(drawStapelPlayer2, 1,3);


        //TODO: mit Array schöner gestalten
        for(int i = 0; i<5; i++){
            Button actionButton = new Button("Aktionskarte");
            actionPane.add(actionButton, i, 0);
        }

        //TODO: mit Array schöner gestalten
        for(int i = 0; i<5; i++){
            Button actionButton = new Button("Aktionskarte");
            actionPane.add(actionButton, i, 1);
        }

        //TODO: mit Array schöner gestalten
        for(int i = 0; i<5; i++){
            Button player1Card = new Button("Handkarte");
            player1Box.getChildren().add(player1Card);
        }

        //TODO: mit Array schöner gestalten
        for(int i = 0; i<5; i++){
            Button player2Card = new Button("Handkarte");
            player2Box.getChildren().add(player2Card);
        }





        //Scene Initialisieren
        Scene scene = new Scene(root, 1000, 800);
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

