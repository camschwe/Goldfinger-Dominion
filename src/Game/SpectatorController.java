package Game;

import Localisation.Localisator;

public class SpectatorController {
    private GameView gameView;
    private Localisator localisator;
    private GameModel gameModel;
    private FieldCardController fieldCardController;

    public SpectatorController(GameView gameView, Localisator localisator, GameModel gameModel, FieldCardController fieldCardController) {
        this.gameView = gameView;
        this.localisator = localisator;
        this.gameModel = gameModel;
        this.fieldCardController = fieldCardController;
    }
}
