package Game;

import Client_Server.Chat.Message;
import Client_Server.Client.Client;
import Client_Server.GameObject;
import Localisation.Localisator;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


/**
 * Created by camillo.schweizer on 06.10.2017.
 *
 * Controller für die Eigenen Handkarten mit Eventhändlern sowie Supportmethoden
 */
public class HandCardController {

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

        updateHandCardsView();
    }

    /**
     *  Generiert GameButtons für die Handkarten und fügt diese der View hinzu sowie die Eventhandler
     */

    public void updateHandCardsView() {
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
     * Eventhändler für Entered - Die karte wird über CSS grösser dargestellt
     */

    public void addMouseEntered(GameButton gameButton){
        gameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().remove("mediumButton");
                gameButton.getStyleClass().add("bigButton");
            }
        });
    }

    /**
     * Eventhändler für Exited - Kartengrösse wird über CSS wieder resetted.
     */

    public void addMouseExited(GameButton gameButton){
        gameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().remove("bigButton");
                gameButton.getStyleClass().add("mediumButton");
            }
        });
    }

    /**
     * Eventhändler für Klicked - überpüft ob die Handkarte ausgespielt werden darf und ruft danach über die Supportmethode
     * die entsprechende Kartenlogik ab. Danach wird die View aktualisiert und der Kartensound abgespielt.
     */

    public void addMouseKlicked(GameButton gameButton){
        gameButton.setOnAction(event -> {
            Player player = gameModel.getPlayer();
            Card card = gameButton.getCard();

            if(this.actionChecker(card, player) || this.moneyChecker(card, player)){
                cardSwitch(card, player);
                if(!card.getType().equals("point")){
                    gameController.noteFlowUpdate(card, player, 0, Client.getColor());
                    gameController.playSound(gameController.soundUpdate(gameButton.getCard().getName()));
                }
                gameController.player1LabelUpdate();
                gameController.putStapelUpdate(player, gameView.putStapelPlayer1);
                updateHandCardsView();

                GameObject gObject = new GameObject(player, card, 0 );
                gameController.getClient().sendObject(gObject);
                fieldCardController.fieldCardsGlowingUpdate(fieldCardController.getResourceButtons());
                fieldCardController.fieldCardsGlowingUpdate(fieldCardController.getActionButtons());
            }
        });
    }

    /**
     * Supportmethode für den Klick Eventhändler - überprüft, um was für eine Karte es sich handelt und ruft die
     * dazugehörige Methode auf.
     */

    public void cardSwitch(Card card, Player player ){
        String cardName = card.getName();

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
            case "adventurer":
                card.adventurer(player);
                break;
            case "moneylender":
                card.moneylender(player);
                break;
            case "chancellor":
                card.chancellor(player);
                break;
            case "magpie":
                if(card.magpie(player)) {
                    magpieUpdate(player);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Ruft eine Methode auf um zu überprüfen, ob eine Karte ausgespielt werden kann und fügt dieser danach einen Leucht-
     * effekt hinzu.
     */

    public void glowingUpdateHandCards(GameButton gameButton){
        Card card = gameButton.getCard();
        Player player = gameModel.getPlayer();
        if(this.actionChecker(card, player) || this.moneyChecker(card, player)){
            gameButton.getStyleClass().add("buttonOnAction");
        }
    }

    /**
     * Supportmethode um zu überprüfen ob eine Aktionskarte ausgespielt werden kann - gibt einen Boolean zurück
     */

    public boolean actionChecker(Card card, Player player){
        return card.getType().equals("action") &&
                player.isYourTurn() &&
                player.isActionPhase() &&
                player.getActions() > 0;
    }

    /**
     * Supportmethode um zu überprüfen ob eine Geldkarte ausgespielt werden kann - gibt einen Boolean zurück
     */

    public boolean moneyChecker(Card card, Player player){
        return card.getType().equals("money") &&
                 player.isYourTurn() &&
                 player.isBuyPhase();
    }

    /**
     * Supportmethode für die MagpieKarte - Wird ausgelöst, wenn keine Geldkarte gezogen wird. Fügt dem Ablagestapel des
     * Spielers eine Magpiekarte hinzu (insofern ausreichend vorhanden) und führt ruft die Methoden zur Viewaktualisierung auf
     */

    public void magpieUpdate(Player player){
        GameButton magpieButton = new GameButton();

        for(GameButton gameButton:fieldCardController.getActionButtons()){
            if(gameButton.getCard().getName().equals("magpie")){
                magpieButton = gameButton;
            }
        }

        if(magpieButton.getAmount()> 0) {
            magpieButton.setAmount(magpieButton.getAmount() - 1);
            Card cardCopy = Card.cardCopy(magpieButton.getCard());
            player.getPlayDeck().add(cardCopy);
            updateHandCardsView();
            fieldCardController.buttonAmountUpdate(magpieButton);
            gameController.getClient().sendObject(new GameObject(player, cardCopy, 1));
            fieldCardController.fieldCardsGlowingUpdate(fieldCardController.getActionButtons());
        }
    }
}
