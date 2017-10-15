package Game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


/**
 * Created by camillo.schweizer on 11.10.2017.
 */
public class FieldCardController {

    private GameView gameView;
    private GameModel gameModel;


    public FieldCardController(GameView gameView, GameModel gameModel) {
        this.gameView = gameView;
        this.gameModel = gameModel;


       // estateButton = new Button();
        //estateButton.getStyleClass().add("estateSmall");
        //duchyButton = new Button();
        //duchyButton.getStyleClass().add("duchySmall");
        //provinceButton = new Button();
        //provinceButton.getStyleClass().add("provinceSmall");
        //copperButton = new Button();
        //copperButton.getStyleClass().add("copperSmall");
        //silverButton = new Button();
        //silverButton.getStyleClass().add("silverSmall");
        //goldButton = new Button();
        //goldButton.getStyleClass().add("goldSmall");
        //resourceButton = new Button();
        //resourceButton.getStyleClass().add("invisible");



        //resourcePane.add(provinceButton, 0,0);
        //resourcePane.add(duchyButton, 0,1);
        //resourcePane.add(estateButton, 0,2);
        //resourcePane.add(goldButton, 1,0);
        //resourcePane.add(silverButton, 1,1);
        //resourcePane.add(copperButton, 1,2);


        for(int i = 0; i < gameModel.getMoneyCards().size() ; i++) {
            GameButton moneyButton = new GameButton(gameModel.getMoneyCards().get(i));
            moneyButton.getStyleClass().add(moneyButton.getCard().getCardName()+"Small");
            gameView.resourcePane.add(moneyButton, 1,i);
            addMouseExited(moneyButton);
            addMouseEntered(moneyButton);
            addMouseKlickedMoney(moneyButton);


        }

        for(int i = 0; i < gameModel.getPointCards().size() ; i++) {
            GameButton pointButton = new GameButton(gameModel.getPointCards().get(i));
            pointButton.getStyleClass().add(pointButton.getCard().getCardName()+"Small");
            gameView.resourcePane.add(pointButton, 0,i);
            addMouseExited(pointButton);
            addMouseEntered(pointButton);
            addMouseKlickedPoint(pointButton);


        }

    }
    public void addMouseEntered(GameButton gameButton){

        gameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().clear();
                gameView.resourceButton.getStyleClass().add(gameButton.getCard().getCardName()+"Big");
                System.out.println(gameButton.getCard().getCardName()+"Big");

            }
        });

    }

    public void addMouseExited(GameButton gameButton){

        gameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().add("invisible");

            }
        });

    }

    //Todo: Anzahl Karten auf dem Stapel abfragen und vermindern
    //TODO: Label mit Properties
    public void addMouseKlickedMoney(GameButton gameButton){
        gameButton.setOnAction(event -> {
            if(gameModel.getPlayer().isYourTurn()&& gameButton.getCard().getCost() <= gameModel.getPlayer().getMoney()){
                gameModel.getPlayer().getHandCards().add(gameButton.getCard());
                gameModel.getPlayer().setMoney(gameModel.getPlayer().getMoney() - gameButton.getCard().getCost());
                gameView.moneyLabel1.setText("money"+ ":\t"+gameModel.getPlayer().getMoney());
            }
        });
    }

    //TODO: IMPLEMENTIEREN
    public void addMouseKlickedPoint(GameButton gameButton){
        gameButton.setOnAction(event -> {

        });
    }




}
