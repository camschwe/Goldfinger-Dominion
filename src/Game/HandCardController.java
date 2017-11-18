package Game;

import Client_Server.Client.Client;
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

        updateHandCardsView();

    }

    /**
     * Initialisierung der Handkarten sowie hinzufügen der Eventhändler
     */

    //Generiert Buttons für die Handkarten und fügt diese dem GU Hinzu sowie die Eventhandler
    //TODO: Aktualisierung am Zugende implementieren
    public void updateHandCardsView() {
        gameView.player1Box.getChildren().clear();
        System.out.println(gameModel.getPlayer().isYourTurn());
        System.out.println(gameModel.getPlayer().getPlayerName());


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
                if(!card.getType().equals("point")){
                    gameController.noteFlowUpdate(card, player, 0, Client.getColor());
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
                player.isYourTurn() &&
                player.isActionPhase() &&
                player.getActions() > 0;

    }

    public boolean moneyChecker(Card card, Player player){
        return card.getType().equals("money") &&
                 player.isYourTurn() &&
                 player.isBuyPhase();
    }
}
