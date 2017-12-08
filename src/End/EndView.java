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
    public Button leaveButton;
    public Label resultLabel, turnLabel;
    protected Localisator localisator;
    public GridPane gridPane;
    public Scene scene;
    String resolution;


    public EndView(Stage gameStage, Localisator localisator, String turns, String resolution) {
        this.gameStage = gameStage;
        this.localisator = localisator;
        this.resolution = resolution;

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


        hBox.setPadding(new Insets(0, 0, 40, 740));
        hBox.getChildren().add(leaveButton);

        resultLabel = new Label(localisator.getResourceBundle().getString("lost"));
        turnLabel = new Label(turns);
        turnLabel.getStyleClass().add("turnLabel");
        resultLabel.getStyleClass().add("resultLabel");
        gridPane.add(turnLabel, 0,0);
        root.setTop(resultLabel);


        //Scene Initialisieren

        if(resolution.equals("720p")){
            scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("../Stylesheets/EndStylesSmall.css").toExternalForm());
        }else{
            scene = new Scene(root, 1920, 1080);
            scene.getStylesheets().add(getClass().getResource("../Stylesheets/EndStylesBig.css").toExternalForm());
        }
        gameStage.setScene(scene);

        gameStage.getIcons().add(new Image("Backgrounds/DominionSchildTransparent.png"));
        gameStage.setTitle("Goldfinger Dominion");

    }

    public void start() {
        gameStage.show();
    }

    public void stop() {
        gameStage.hide();
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}

