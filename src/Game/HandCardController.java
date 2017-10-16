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

    }

    //Aktualisiert die Handkarten im GUI
    //TODO: Aktualisierung am Zugende implementieren
    public void updateHandcardsView() {
        gameView.player1Box.getChildren().clear();

        for (int i = 0; i < gameModel.getPlayer().getHandCards().size(); i++) {
            Player player = gameModel.getPlayer();
            Card card = player.getHandCards().get(i);

            GameButton player1Card = new GameButton(card);
            player1Card.getStyleClass().add(card.getCardName());
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
            cardChecker(gameButton, card, player);
        });
    }

    //Switch zur überprüfung der gedrückten Handkarte
    public void cardChecker(GameButton gameButton, Card card, Player player){
        String cardName = gameButton.getCard().getCardName();

        switch (cardName) {
            case "copper":
                moneyUpdate(gameButton, card, player);
                break;
            case "village":
                villageUpdate(gameButton);
                break;
            default:
                break;
        }
    }


    //Führ das Update nach Klick auf eine Geldkarte durch
    public void moneyUpdate(GameButton gameButton, Card card, Player player){
        if(player.isYourTurn()){
            player.setMoney(player.getMoney()+ card.getValue());
            gameView.moneyLabel1.setText(localisator.getResourceBundle().getString("money")+ ":\t"+player.getMoney());
            gameView.player1Box.getChildren().remove(gameButton);

            int i = 0;
            boolean checker = false;
            while(player.getHandCards().size() > i || !checker){
                if(card.equals(player.getHandCards().get(i))){
                    player.getPutDeck().add(player.getHandCards().get(i));
                    player.getHandCards().remove(i);
                    checker = true;
                }
                i++;
            }

        }
    }

    public void villageUpdate(GameButton gameButton){
        Card.village(gameModel.getPlayer());
    }


}
