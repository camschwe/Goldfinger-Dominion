package Game;

import Localisation.Localisator;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import java.util.ArrayList;

/**
 * Created by Benjamin Probst on 10.11.2017.
 **/


public class SpectatorController {
    private GameView gameView;
    private Localisator localisator;
    private GameModel gameModel;
    private FieldCardController fieldCardController;
    private final int initial = 5;

    public SpectatorController(GameView gameView, Localisator localisator, GameModel gameModel, FieldCardController fieldCardController) {
        this.gameView = gameView;
        this.localisator = localisator;
        this.gameModel = gameModel;
        this.fieldCardController = fieldCardController;
        fillPlayer(1);
    }

    // Methode zum hinzufügen der Spielerkarten, Aktiver Spieler und Letzter Spieler
    private void fillPlayer(int i) {
        for (int amount = 0; amount < initial; amount++) {
            Button button = new Button();
            button.getStyleClass().add("mediumButton");
            button.getStyleClass().add("back2");
            if (i == 1){
                gameView.player1Box.getChildren().add(button);
            } else if (i == 2){
                gameView.player2Box.getChildren().add(button);
            }
        }
    }

    // Methode welche beim Abschluss des Zuges die Karten von oben nach unten verschiebt
    public void changePlayerCards(){
        ArrayList<Button> buttons = new ArrayList<>();
        for (Node child : gameView.player2Box.getChildren()) {
            buttons.add((Button) child);
        }
        Platform.runLater(() -> {
            gameView.player1Box.getChildren().clear();
            for (Button button : buttons){
                gameView.player1Box.getChildren().add(button);
                addMouseEntered(button);
                addMouseExited(button);
            }
            gameView.playerLabel1.setText(gameView.playerLabel2.getText());
            gameView.pointLabel1.setText(gameView.pointLabel2.getText());
            fillPlayer(2);
        });
    }

    /**
     * Eventhändler für Entered, Exited und Klicked
     */

    //Action Event Handcards Mouse Entered
    public void addMouseEntered(Button gameButton){
        gameButton.setOnMouseEntered(mouseEvent -> {
            gameButton.getStyleClass().remove("mediumButton");
            gameButton.getStyleClass().add("bigButton");
        });
    }

    // Action Event Handcards Mouse Left
    public void addMouseExited(Button gameButton){
        gameButton.setOnMouseExited(mouseEvent -> {
            gameButton.getStyleClass().remove("bigButton");
            gameButton.getStyleClass().add("mediumButton");
        });
    }
}
