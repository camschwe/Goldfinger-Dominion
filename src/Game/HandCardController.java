package Game;

import Client_Server.Chat.Message;
import Client_Server.GameObject;
import Localisation.Localisator;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class HandCardController {

    /**
     * Controller für die Handkarten mit Initalisierung und Eventhändlern
     */

    private GameView gameView;
    private Localisator localisator;
    private GameModel gameModel;
    private GameController gameController;
    private FieldCardController fieldCardController;

    public HandCardController(GameView gameView, Localisator localisator, GameModel gameModel, GameController gameController, FieldCardController fieldCardController) {
        this.gameView = gameView;
        this.localisator = localisator;
        this.gameModel = gameModel;
        this.gameController = gameController;
        this.fieldCardController = fieldCardController;

        updateHandcardsView();

        //Eventhändler für den Phase Button
        gameView.phaseButton.setOnAction(event -> {
            if(gameModel.getPlayer().isYourTurn()) {
                gameModel.getPlayer().endPhase();
                updateHandcardsView();
                gameController.player1LabelUpdate();
                fieldCardController.fieldCardsGlowingUpdate();
            }
            if (gameModel.getPlayer().isTurnEnded()){
                gameController.getClient().sendObject(new Message(6, gameModel.getPlayer().getPlayerName(), "Turn ended"));
                gameModel.getPlayer().setTurnEnded(false);
            }

        });
    }

    /**
     * Initialisierung der Handkarten sowie hinzufügen der Eventhändler
     */

    //Generiert Buttons für die Handkarten und fügt diese dem GU Hinzu sowie die Eventhandler
    //TODO: Aktualisierung am Zugende implementieren
    public void updateHandcardsView() {
        gameView.player1Box.getChildren().clear();

        for (int i = 0; i < gameModel.getPlayer().getHandCards().size(); i++) {
            GameButton gameButton = new GameButton(gameModel.getPlayer().getHandCards().get(i));
            gameView.player1Box.getChildren().add(gameButton);
            addMouseEntered(gameButton);
            addMouseExited(gameButton);
            addMouseKlicked(gameButton);
            glowingUpdateHandCards(gameButton);
        }
    }

    /**
     * Eventhändler für Entered, Exited und Klicked
     */

    //Action Event Handcards Mouse Entered
    public void addMouseEntered(GameButton gameButton){
        gameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().remove("mediumButton");
                gameButton.getStyleClass().add("bigButton");
            }
        });
    }

    // Action Event Handcards Mouse Left
    public void addMouseExited(GameButton gameButton){
        gameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().remove("bigButton");
                gameButton.getStyleClass().add("mediumButton");
            }
        });
    }

    //Eventhändler für Klick auf Handkarte
    public void addMouseKlicked(GameButton gameButton){
        gameButton.setOnAction(event -> {
            Player player = gameModel.getPlayer();
            Card card = gameButton.getCard();

            if(player.isYourTurn()){
                cardSwitch(card, player);
                gameController.noteFlowUpdate(card, player, 0);
                gameController.player1LabelUpdate();
                gameController.putStapelUpdate(player, gameView.putStapelPlayer1);
                updateHandcardsView();
                System.out.println("controller übermittlung");
                for(int i = 0 ; i < gameModel.getPlayer().getPlayDeck().size(); i++)
                    System.out.print(" "+gameModel.getPlayer().getPlayDeck().get(i).getName());

                System.out.println("\n");
                gameController.getClient().sendObject(new GameObject(gameModel.getPlayer(), card, 0 ));
                fieldCardController.fieldCardsGlowingUpdate();
            }
            if (player.isTurnEnded()){
                gameController.getClient().sendObject(new Message(6, player.getPlayerName(), "Turn ended"));
                player.setTurnEnded(false);
            }
        });
    }

    /**
     * Klickevent für Geld- und Aktionskarten
     */

    //Switch zur überprüfung der gedrückten Handkarte
    public void cardSwitch(Card card, Player player ){
        String cardName = "!Valid";

        if(this.actionChecker(card, player) || this.moneyChecker(card, player)){
            cardName = card.getName();
        }

        switch (cardName) {
            case "copper":
                card.playMoneyCard(player);
                break;
            case "silver":
                card.playMoneyCard(player);
                break;
            case "gold":
                card.playMoneyCard(player);
                break;
            case "village":
                card.village(player);
                break;
            case "fair":
                card.fair(player);
                break;
            case "smithy":
                card.smithy(player);
                break;
            case "market":
                card.market(player);
                break;
            case "laboratory":
                card.laboratory(player);
                break;
            case "lumberjack":
                card.lumberjack(player);
                break;
            default:
                break;
        }
    }


    /**
     * Glow Effekt hinzuzufügen und wieder zu entfernen
     */

    public void glowingUpdateHandCards(GameButton gameButton){
        Card card = gameButton.getCard();
        Player player = gameModel.getPlayer();
        if(this.actionChecker(card, player) || this.moneyChecker(card, player)){
            gameButton.getStyleClass().add("buttonOnAction");
        }
    }

    public boolean actionChecker(Card card, Player player){
        return card.getType().equals("action") &&
                player.isActionPhase() &&
                player.getActions() > 0;

    }

    public boolean moneyChecker(Card card, Player player){
        return card.getType().equals("money")
                && player.isBuyPhase();
    }
}
