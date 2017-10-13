package Game;

import Localisation.Localisator;
import javafx.scene.control.Button;


/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class GameController {


    private GameView gameView;
    private Localisator localisator;
    private GameModel gameModel;

    public GameController(GameView gameView, Localisator localisator, GameModel gameModel) {
        this.gameView = gameView;
        this.localisator = localisator;
        this.gameModel = gameModel;

        updateHandcards();

        gameView.copperButton.setOnAction(event -> {
            updateHandcards();
        });

    }

    public void updateHandcards(){
        gameView.player1Box.getChildren().clear();

        for(int i = 0; i < gameModel.getPlayer().getHandCards().size(); i++){
            Button player1Card = new Button();
            player1Card.getStyleClass().add(gameModel.getPlayer().getHandCards().get(i).getCardName());
            gameView.player1Box.getChildren().add(player1Card);



        }

    }


}
