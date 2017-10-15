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
    public void updateHandcardsView() {
        gameView.player1Box.getChildren().clear();

        for (int i = 0; i < gameModel.getPlayer().getHandCards().size(); i++) {
            GameButton player1Card = new GameButton(gameModel.getPlayer().getHandCards().get(i));
            player1Card.getStyleClass().add(gameModel.getPlayer().getHandCards().get(i).getCardName());
            gameView.player1Box.getChildren().add(player1Card);
            addMouseEntered(player1Card);
            addMouseExited(player1Card);
            addMouseKlicked(player1Card);


        }

    }

    //Action Event Handcards Mouse Entered
    public void addMouseEntered(GameButton gameButton){
        gameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().clear();
                gameButton.getStyleClass().add(gameButton.getCard().getCardName() + "Big");

            }
        });


    }

    // Action Event Handcards Mouse Left
    public void addMouseExited(GameButton gameButton){
        gameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameButton.getStyleClass().clear();
                gameButton.getStyleClass().add(gameButton.getCard().getCardName());

            }
        });
    }

    public void addMouseKlicked(GameButton gameButton){
        gameButton.setOnAction(event -> {
            cardChecker(gameButton);
        });
    }

    public void cardChecker(GameButton gameButton){
        String cardName = gameButton.getCard().getCardName();

        switch (cardName) {
            case "copper":
                moneyUpdate(gameButton);
                break;
            case "village":
                villageUpdate(gameButton);
                break;
            default:
                break;
        }
    }


    public void moneyUpdate(GameButton gameButton){
        if(gameModel.getPlayer().isYourTurn()){
            gameModel.getPlayer().setMoney(gameModel.getPlayer().getMoney()+ gameButton.getCard().getValue());
            gameView.moneyLabel1.setText(localisator.getResourceBundle().getString("money")+ ":\t"+gameModel.getPlayer().getMoney());
            gameView.player1Box.getChildren().remove(gameButton);

            int i = 0;
            boolean checker = false;
            while(gameModel.getPlayer().getHandCards().size() > i || !checker){
                if(gameButton.getCard().equals(gameModel.getPlayer().getHandCards().get(i))){
                    gameModel.getPlayer().getPutDeck().add(gameModel.getPlayer().getHandCards().get(i));
                    gameModel.getPlayer().getHandCards().remove(i);
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
