package Game;

import Localisation.Localisator;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;

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

    public void changePlayerCards(){
        ArrayList<Button> buttons = new ArrayList<>();
        for (Node child : gameView.player2Box.getChildren()) {
            buttons.add((Button) child);
        }
        Platform.runLater(() -> {
            gameView.player1Box.getChildren().clear();
            for (Button button : buttons){
                gameView.player1Box.getChildren().add(button);
            }
            gameView.playerLabel1.setText(gameView.playerLabel2.getText());
            fillPlayer(2);
        });
    }
}
