package Game;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

