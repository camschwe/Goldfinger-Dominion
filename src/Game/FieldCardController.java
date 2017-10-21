package Game;

import Localisation.Localisator;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


/**
 * Created by camillo.schweizer on 11.10.2017.
 */
public class FieldCardController {

    private GameView gameView;
    private GameModel gameModel;
    private Localisator localisator;
    private HandCardController handCardController;
    private GameController gameController;


    public FieldCardController(GameView gameView, Localisator localisator, GameModel gameModel, HandCardController handCardController, GameController gameController) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        this.localisator = localisator;
        this.handCardController = handCardController;
        this.gameController = gameController;

        //TODO: ADD AMOUNT REFERRING PLAYER
        //Initialisierung der Geldkarten
        for(int i = 0; i < gameModel.getMoneyCards().size() ; i++) {
            GameButton moneyButton = new GameButton(gameModel.getMoneyCards().get(i), 30);
            gameView.resourcePane.add(moneyButton, 1,i);
            addMouseExited(moneyButton, gameView.resourceButton);
            addMouseEntered(moneyButton, gameView.resourceButton);
            addMouseKlicked(moneyButton, gameModel.getPlayer(), moneyButton.getCard());
        }

        //TODO: ADD AMOUNT REFERRING PLAYER
        //Initialisierung Punktekarten
        for(int i = 0; i < gameModel.getPointCards().size() ; i++) {
            GameButton pointButton = new GameButton(gameModel.getPointCards().get(i), 8);
            gameView.resourcePane.add(pointButton, 0,i);
            addMouseExited(pointButton, gameView.resourceButton);
            addMouseEntered(pointButton, gameView.resourceButton);
            addMouseKlicked(pointButton, gameModel.getPlayer(), pointButton.getCard());
        }

        //TODO: ADD AMOUNT REFERRING PLAYER
        //Initialisierung Aktionskarten
        int column = 0;
        int row = 0;
        for(int i = 0; i < gameModel.getActionCards().size(); i++){
            if (i == 5) {
                row = 1;
                column = 0;
            }
            GameButton actionButton = new GameButton(gameModel.getActionCards().get(i), 10);
            gameView.actionPane.add(actionButton, column,row);
            addMouseExited(actionButton, gameView.actionButton);
            addMouseEntered(actionButton, gameView.actionButton);
            addMouseKlicked(actionButton, gameModel.getPlayer(), actionButton.getCard());
            column ++;
        }
    }

    //fügt Effekt für Mouseover hizu
    //TODO: MIT TOOLTIP LöSEN
    public void addMouseEntered(GameButton gameButton, Button showButton){
        gameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showButton.getStyleClass().clear();
                showButton.getStyleClass().add(gameButton.getCard().getCardName()+"Big");
            }
        });
    }
    //Fügt Effekt für Mouse Exited hinzu
    public void addMouseExited(GameButton gameButton, Button showButton){
        gameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showButton.getStyleClass().add("invisible");
            }
        });
    }

    //TODO: ALLE 3 EVENTHÄNDLER ZUSAMMENLEGEN?
    //Eventhändler für Klick auf einen Aktionskarte auf dem Spielfeld
    public void addMouseKlicked(GameButton gameButton, Player player, Card card){
        gameButton.setOnAction(event -> {
            if (buyChecker(gameButton, player, card)){
                Card cardCoppy = Card.cardCopy(card);
                player.buyCard(cardCoppy);
                buyUpdateView(gameButton, player);
                gameController.putStapelUpdate(player, gameView.putStapelPlayer1);
                if(card.getCardName().equals("province") && gameButton.getAmount() < 1) {
                    gameView.stop();
                }
            }



        });
    }


    //überprüft ob eine Karte gekauft werden kann
    public boolean buyChecker(GameButton gameButton, Player player, Card card) {
        return player.isYourTurn() &&
                player.isBuyPhase() &&
                player.getBuys() > 0 &&
                card.getCost() <= player.getMoney() &&
                gameButton.getAmount() > 0;
    }

    //Aktualisiert die Spielerlabel
    public void buyUpdateView(GameButton gameButton, Player player){
        gameView.moneyLabel1.setText(localisator.getResourceBundle().getString("money") + ":\t" + player.getMoney());
        gameButton.setAmount(gameButton.getAmount() - 1);
        handCardController.updateLabel();
        handCardController.updateHandcardsView();
    }



}
