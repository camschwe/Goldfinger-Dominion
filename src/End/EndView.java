package Login;

import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class EndView {

    protected Stage gameStage;
    public Button lobbyButton, leaveButton;
    public Label resultLabel, turnLabel;
    protected Localisator localisator;
    public GridPane gridPane;
    public Scene scene;


    public EndView(Stage gameStage, Localisator localisator) {
        this.gameStage = gameStage;
        this.localisator = localisator;

        BorderPane root = new BorderPane();
        gridPane = new GridPane();
        root.setCenter(gridPane);
        gridPane.setPadding(new Insets(100, 0, 0, 200));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        HBox hBox = new HBox();
        hBox.setSpacing(40);
        root.setBottom(hBox);

        leaveButton = new Button(localisator.getResourceBundle().getString("leave"));
        lobbyButton = new Button(localisator.getResourceBundle().getString("lobby"));

        hBox.setPadding(new Insets(0, 0, 40, 740));
        hBox.getChildren().addAll(lobbyButton, leaveButton);

        resultLabel = new Label(localisator.getResourceBundle().getString("lost"));
        turnLabel = new Label(localisator.getResourceBundle().getString("turnsDone"));
        turnLabel.getStyleClass().add("turnLabel");
        resultLabel.getStyleClass().add("resultLabel");
        gridPane.add(turnLabel, 0,0);
        root.setTop(resultLabel);


        //Scene Initialisieren
        scene = new Scene(root, 1920, 1080);
        gameStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../Stylesheets/EndStylesBig.css").toExternalForm());
        gameStage.getIcons().add(new Image("Backgrounds/DominionSchildTransparent.png"));
        gameStage.setTitle("Goldfinger Dominion");
        gameStage.setMaxWidth(1920);
        gameStage.setMaxHeight(1080);
        gameStage.setMinWidth(1920);
        gameStage.setMinHeight(1080);
    }

    public void start() {
        gameStage.show();
    }

    public void stop() {
        gameStage.hide();

    }

}

