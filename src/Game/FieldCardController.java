package Game;

import Client_Server.Chat.Message;
import Client_Server.Client.Client;
import Client_Server.GameObject;
import Localisation.Localisator;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;


/**
 * Created by camillo.schweizer on 11.10.2017.
 */
public class FieldCardController {

    /**
     * Controller für die Feldkarten mit Initalisierung und Eventhändlern
     */

    private GameView gameView;
    private GameModel gameModel;
    private Localisator localisator;
    private HandCardController handCardController;
    private GameController gameController;
    private ArrayList<GameButton> actionButtons;
    private ArrayList<GameButton> resourceButtons;
    private SpectatorController spectatorController;


    public FieldCardController(GameView gameView, Localisator localisator, GameModel gameModel, GameController gameController) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        this.localisator = localisator;
        this.handCardController = handCardController;
        this.gameController = gameController;
        this.actionButtons = new ArrayList<>();
        this.resourceButtons = new ArrayList<>();

        if (!gameController.getClient().isGameStarted()) {
            handCardController = new HandCardController(gameView, localisator, gameModel, gameController, this);
        } else{
            spectatorController = new SpectatorController(gameView, localisator, gameModel, this);
        }

        /**
         * Jeweils eine Methode zur Initialisierung der Geld, Punkte und Aktionskarten auf dem Spielfeld anhand
         * dem Array aus dem GameModel.Zudem Eventhändler für die Feldkarten sowie Methode für den Glow Effekt.
         * */

        //TODO: ADD AMOUNT REFERRING PLAYER
        //Initialisierung der Geldkarten
        for(int i = 0; i < gameModel.getMoneyCards().size() ; i++) {
            GameButton moneyButton = new GameButton(gameModel.getMoneyCards().get(i), 30);
            gameView.resourcePane.add(moneyButton, 1,i);
            resourceButtons.add(moneyButton);
            addMouseExited(moneyButton, gameView.actionButton);
            addMouseEntered(moneyButton, gameView.actionButton);
            addMouseKlicked(moneyButton, gameModel.getPlayer(), moneyButton.getCard());

        }

        //TODO: ADD AMOUNT REFERRING PLAYER
        //Initialisierung Punktekarten
        for(int i = 0; i < gameModel.getPointCards().size() ; i++) {
            GameButton pointButton = new GameButton(gameModel.getPointCards().get(i), 8);
            gameView.resourcePane.add(pointButton, 0,i);
            resourceButtons.add(pointButton);
            addMouseExited(pointButton, gameView.actionButton);
            addMouseEntered(pointButton, gameView.actionButton);
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

    /**
     * Eventhändler für das GUI und Klick
     */

    //fügt Effekt für Mouseover hizu
    //TODO: MIT TOOLTIP LöSEN
    public void addMouseEntered(GameButton gameButton, Button showButton){
        gameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showButton.getStyleClass().clear();
                showButton.getStyleClass().add("bigButton");
                showButton.getStyleClass().add(gameButton.getCard().getName());
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
                fieldCardsGlowingUpdate(resourceButtons);
                fieldCardsGlowingUpdate(actionButtons);
                buttonAmountUpdate(gameButton);
                gameController.noteFlowUpdate( card, player, 1, Client.getColor());

             }

        });
    }


    /**
     * Methoden für den Klickevent beziehungsweise Kauf einer Karte
     */

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
        gameController.player1LabelUpdate();
        handCardController.updateHandcardsView();
        gameController.getClient().sendObject(new GameObject(player, card, 1 ));
        if(card.getName().equals("province") && gameButton.getAmount() < 1) {
            gameController.getClient().sendObject(new Message(4, player.getPlayerName(), "EndGame"));
            gameView.stop();
        }
        if (player.isTurnEnded()){
            gameController.getClient().sendObject(new Message(6, player.getPlayerName(), "Turn ended"));
            player.setTurnEnded(false);
        }
    }

    /**
     * Methode um den Glow Effekt hinzuzufügen und zu entfernen
     */

    //Aktualisiert den Glow Effekt
    public void fieldCardsGlowingUpdate(ArrayList<GameButton> gameButtons){

        for(int i = 0; i<gameButtons.size(); i++) {
            gameButtons.get(i).getStyleClass().remove("buttonOnAction");
            if (gameModel.getPlayer().isBuyPhase() &&
                    gameButtons.get(i).getAmount() > 0 &&
                    gameButtons.get(i).getCard().getCost() <= gameModel.getPlayer().getMoney()) {
                gameButtons.get(i).getStyleClass().add("buttonOnAction");
            }else if (gameButtons.get(i).getAmount() < 1){
                gameButtons.get(i).getStyleClass().add("buttonOpacity");
            }
        }


    }

    public void buttonAmountUpdate(GameButton gameButton){
        gameButton.setText(""+gameButton.getAmount());
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public Localisator getLocalisator() {
        return localisator;
    }

    public void setLocalisator(Localisator localisator) {
        this.localisator = localisator;
    }

    public HandCardController getHandCardController() {
        return handCardController;
    }

    public void setHandCardController(HandCardController handCardController) {
        this.handCardController = handCardController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public ArrayList<GameButton> getActionButtons() {
        return actionButtons;
    }

    public void setActionButtons(ArrayList<GameButton> actionButtons) {
        this.actionButtons = actionButtons;
    }

    public ArrayList<GameButton> getResourceButtons() {
        return resourceButtons;
    }

    public void setResourceButtons(ArrayList<GameButton> resourceButtons) {
        this.resourceButtons = resourceButtons;
    }

    public SpectatorController getSpectatorController() {
        return spectatorController;
    }
}
