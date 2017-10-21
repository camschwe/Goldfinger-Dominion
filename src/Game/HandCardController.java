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

    public HandCardController(GameView gameView, Localisator localisator, GameModel gameModel) {
        this.gameView = gameView;
        this.localisator = localisator;
        this.gameModel = gameModel;

        updateHandcardsView();

        //Eventhändler für den Phase Button
        gameView.phaseButton.setOnAction(event -> {
            if(gameModel.getPlayer().isYourTurn()) {
                gameModel.getPlayer().endPhase();
                updateHandcardsView();
                updateLabel();
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
        }
    }

    //Action Event Handcards Mouse Entered
    public void addMouseEntered(GameButton gameButton, Card card){
        gameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().clear();
                gameButton.getStyleClass().add(card.getCardName() + "Big");
            }
        });
    }

    // Action Event Handcards Mouse Left
    public void addMouseExited(GameButton gameButton, Card card){
        gameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().clear();
                gameButton.getStyleClass().add(card.getCardName());
            }
        });
    }

    //Eventhändler für Klick auf Handkarte
    public void addMouseKlicked(GameButton gameButton, Card card, Player player){
        gameButton.setOnAction(event -> {
            if(player.isYourTurn()){
                cardChecker(gameButton, card, player);
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
            gameView.moneyLabel1.setText(localisator.getResourceBundle().getString("money") + ":\t" + player.getMoney());
            updateHandcardsView();
        }
    }

    //überprüft ob die Village Karte ausgespielt werden kann und startet die Methode
    public void villageUpdate(Player player, Card card){
        if(player.isActionPhase()) {
            player.village(card);
            updateHandcardsView();
        }
    }

    //Spielerlabel werden aktualisiert
    public void updateLabel(){
        Player player = gameModel.getPlayer();
        gameView.moneyLabel1.setText(localisator.getResourceBundle().getString("money")+ ":\t"+player.getMoney());
        if(player.isActionPhase()){
            gameView.phaseLabel1.setText(localisator.getResourceBundle().getString("phase")+
                    ":\t" +localisator.getResourceBundle().getString( "action"));
        }else{gameView.phaseLabel1.setText(localisator.getResourceBundle().getString("phase")+
                ":\t" +localisator.getResourceBundle().getString( "buy"));
        }
    }
}
