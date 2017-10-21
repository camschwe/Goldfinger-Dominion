package Game;

import Localisation.Localisator;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


/**
 * Created by camillo.schweizer on 06.10.2017.
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

        updateHandcardsView();

        //Eventhändler für den Phase Button
        gameView.phaseButton.setOnAction(event -> {
            if(gameModel.getPlayer().isYourTurn()) {
                gameModel.getPlayer().endPhase();
                updateHandcardsView();
                gameController.updateLabel();
                fieldCardController.fieldCardsGlowingUpdate();
            }
        });
    }

    //Generiert Buttons für die Handkarten und fügt diese dem GU Hinzu sowie die Eventhandler
    //TODO: Aktualisierung am Zugende implementieren
    public void updateHandcardsView() {
        gameView.player1Box.getChildren().clear();


        for (int i = 0; i < gameModel.getPlayer().getHandCards().size(); i++) {
            Player player = gameModel.getPlayer();
            Card card = player.getHandCards().get(i);

            GameButton player1Card = new GameButton(card);
            gameView.player1Box.getChildren().add(player1Card);
            addMouseEntered(player1Card, card);
            addMouseExited(player1Card, card);
            addMouseKlicked(player1Card, card, player);
            glowingUpdateHandCards(player1Card, gameModel.getPlayer());
        }
    }

    //Action Event Handcards Mouse Entered
    public void addMouseEntered(GameButton gameButton, Card card){
        gameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().remove("mediumButton");
                gameButton.getStyleClass().add("bigButton");
            }
        });
    }

    // Action Event Handcards Mouse Left
    public void addMouseExited(GameButton gameButton, Card card){
        gameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().remove("bigButton");
                gameButton.getStyleClass().add("mediumButton");
            }
        });
    }

    //Eventhändler für Klick auf Handkarte
    public void addMouseKlicked(GameButton gameButton, Card card, Player player){
        gameButton.setOnAction(event -> {
            if(player.isYourTurn()){
                cardChecker(gameButton, card, player);
                gameController.updateLabel();
                gameController.putStapelUpdate(player, gameView.putStapelPlayer1);
                updateHandcardsView();
                fieldCardController.fieldCardsGlowingUpdate();
            }

        });
    }

    //Switch zur überprüfung der gedrückten Handkarte
    public void cardChecker(GameButton gameButton, Card card, Player player){
        String cardName = gameButton.getCard().getCardName();

        switch (cardName) {
            case "copper":
                moneyUpdate(card, player);
                break;
            case "silver":
                moneyUpdate(card, player);
                break;
            case "gold":
                moneyUpdate(card, player);
                break;
            case "village":
                villageUpdate(player, card);
                break;
            default:
                break;
        }
    }


    //Führt das Update nach Klick auf eine Geldkarte durch
    public void moneyUpdate(Card card, Player player){
        if(player.isBuyPhase()) {
            player.playMoneyCard(card);
        }
    }

    //überprüft ob die Village Karte ausgespielt werden kann und startet die Methode
    public void villageUpdate(Player player, Card card){
        if(player.isActionPhase()) {
            player.village(card);
        }
    }

    //Spielerlabel werden aktualisiert


    public void glowingUpdateHandCards(GameButton gameButton, Player player){
        if(player.isActionPhase() && gameButton.getCard().getType().equals("action")){
            gameButton.getStyleClass().add("buttonOnAction");
        }

        if(player.isBuyPhase() && gameButton.getCard().getType().equals("money")){
            gameButton.getStyleClass().add("buttonOnAction");
        }
    }
}
