package Game;

import Game.GameView.GameView;
import Localisation.Localisator;


/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class GameController {


    private GameView gameView;
    private Localisator localisator;

    public GameController(GameView gameView, Localisator localisator) {
        this.gameView = gameView;
        this.localisator = localisator;

    }
}
