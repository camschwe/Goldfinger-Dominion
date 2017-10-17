package Game;

import Localisation.Localisator;
import javafx.application.Platform;
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


    public FieldCardController(GameView gameView, Localisator localisator, GameModel gameModel, HandCardController handCardController) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        this.localisator = localisator;
        this.handCardController = handCardController;






        //TODO: ADD AMOUNT REFERRING PLAYER
        //Initialisierung der Geldkarten
        for(int i = 0; i < gameModel.getMoneyCards().size() ; i++) {
            GameButton moneyButton = new GameButton(gameModel.getMoneyCards().get(i), 30);
            gameView.resourcePane.add(moneyButton, 1,i);
            addMouseExited(moneyButton, gameView.resourceButton);
            addMouseEntered(moneyButton, gameView.resourceButton);
            addMouseKlickedMoney(moneyButton, gameModel.getPlayer(), moneyButton.getCard());

        }

        //TODO: ADD AMOUNT REFERRING PLAYER
        //Initialisierung Punktekarten
        for(int i = 0; i < gameModel.getPointCards().size() ; i++) {
            GameButton pointButton = new GameButton(gameModel.getPointCards().get(i), 8);
            gameView.resourcePane.add(pointButton, 0,i);
            addMouseExited(pointButton, gameView.resourceButton);
            addMouseEntered(pointButton, gameView.resourceButton);
            addMouseKlickedPoint(pointButton, gameModel.getPlayer(), pointButton.getCard());

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
            addMouseKlickedAction(actionButton, gameModel.getPlayer(), actionButton.getCard());
            column ++;
        }

    }
    //fügt Effekt für Mouseover hizu
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
    public void addMouseKlickedAction(GameButton gameButton, Player player, Card card){
        gameButton.setOnAction(event -> {
            clickChecker(gameButton, player, card);
            if (clickChecker(gameButton, player, card)){
                Card actionCard = new Card(card.getCardName(), card.getCost(), card.getValue());
                player.buyCard(actionCard);
                buyUpdateView(gameButton, player);
            }




        });
    }

    public void addMouseKlickedMoney(GameButton gameButton, Player player, Card card){
        gameButton.setOnAction(event -> {
            if (clickChecker(gameButton, player, card)){
                Card moneyCard = new Card(card.getCardName(), card.getCost(), card.getValue());
                player.buyCard(moneyCard);
                buyUpdateView(gameButton, player);
            }




        });
    }

    //Eventhändler für Klick auf Punktekarte
    //TODO: IMPLEMENTIEREN
    public void addMouseKlickedPoint(GameButton gameButton, Player player, Card card){
        gameButton.setOnAction(event -> {
            if(clickChecker(gameButton, player, card));{
                Card pointCard = new Card(card.getCardName(), card.getCost(), card.getValue());
                player.buyCard(pointCard);
                buyUpdateView(gameButton, player);
                if(gameButton.getAmount() < 1)
                    gameView.stop();
            }



        });
    }

    public boolean clickChecker(GameButton gameButton, Player player, Card card) {
        if (player.isYourTurn() &&
                player.isBuyPase() &&
                player.getBuys() > 0 &&
                card.getCost() <= player.getMoney() &&
                gameButton.getAmount() > 0) {




        }

        return true;
    }

    public void buyUpdateView(GameButton gameButton, Player player){
        gameView.moneyLabel1.setText(localisator.getResourceBundle().getString("money") + ":\t" + player.getMoney());
        gameButton.setAmount(gameButton.getAmount() - 1);
        handCardController.updateLabel();
        handCardController.updateHandcardsView();

    }




}
