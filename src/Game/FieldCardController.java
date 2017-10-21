package Game;

import Localisation.Localisator;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;


/**
 * Created by camillo.schweizer on 11.10.2017.
 */
public class FieldCardController {

    private GameView gameView;
    private GameModel gameModel;
    private Localisator localisator;
    private HandCardController handCardController;
    private GameController gameController;
    private ArrayList<GameButton> actionButtons;
    private ArrayList<GameButton> resourceButtons;


    public FieldCardController(GameView gameView, Localisator localisator, GameModel gameModel, GameController gameController) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        this.localisator = localisator;
        this.handCardController = handCardController;
        this.gameController = gameController;
        this.actionButtons = new ArrayList<>();
        this.resourceButtons = new ArrayList<>();

        handCardController = new HandCardController(gameView, localisator, gameModel, gameController, this);

        //TODO: ADD AMOUNT REFERRING PLAYER
        //Initialisierung der Geldkarten
        for(int i = 0; i < gameModel.getMoneyCards().size() ; i++) {
            GameButton moneyButton = new GameButton(gameModel.getMoneyCards().get(i), 30);
            gameView.resourcePane.add(moneyButton, 1,i);
            resourceButtons.add(moneyButton);
            addMouseExited(moneyButton, gameView.resourceButton);
            addMouseEntered(moneyButton, gameView.resourceButton);
            addMouseKlicked(moneyButton, gameModel.getPlayer(), moneyButton.getCard());

        }

        //TODO: ADD AMOUNT REFERRING PLAYER
        //Initialisierung Punktekarten
        for(int i = 0; i < gameModel.getPointCards().size() ; i++) {
            GameButton pointButton = new GameButton(gameModel.getPointCards().get(i), 8);
            gameView.resourcePane.add(pointButton, 0,i);
            resourceButtons.add(pointButton);
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
            actionButtons.add(actionButton);
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
                showButton.getStyleClass().remove("invisible");
                showButton.getStyleClass().add(gameButton.getCard().getCardName());
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
            if(buyChecker(gameButton, player, card)){
                buyUpdate(gameButton, player, card);
                fieldCardsGlowingUpdate();
                buttonAmountUpdate(gameButton);
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

    //Führt die Kaufaktion Durch und aktualisiert das GU
    public void buyUpdate(GameButton gameButton, Player player, Card card){
        gameButton.setAmount(gameButton.getAmount() - 1);
        Card cardCopy = Card.cardCopy(card);
        player.buyCard(cardCopy);
        gameController.putStapelUpdate(player, gameView.putStapelPlayer1);
        gameController.updateLabel();
        handCardController.updateHandcardsView();
        if(card.getCardName().equals("province") && gameButton.getAmount() < 1) {
            gameView.stop();
        }

    }

    //Aktualisiert den Glow Effekt
    public void fieldCardsGlowingUpdate(){


        for(int i = 0; i<actionButtons.size(); i++) {
            actionButtons.get(i).getStyleClass().remove("buttonOnAction");
            if (gameModel.getPlayer().isBuyPhase() &&
                    actionButtons.get(i).getCard().getCost() <= gameModel.getPlayer().getMoney()) {
                actionButtons.get(i).getStyleClass().add("buttonOnAction");
            }
        }

        for(int i = 0; i<resourceButtons.size(); i++){
            resourceButtons.get(i).getStyleClass().remove("buttonOnAction");
            if(gameModel.getPlayer().isBuyPhase() &&
                    resourceButtons.get(i).getCard().getCost() <= gameModel.getPlayer().getMoney()){
                resourceButtons.get(i).getStyleClass().add("buttonOnAction");

            }
        }
    }

    public void buttonAmountUpdate(GameButton gameButton){
        gameButton.setText(""+gameButton.getAmount());
    }



}
