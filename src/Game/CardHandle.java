package Game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


/**
 * Created by camillo.schweizer on 11.10.2017.
 */
public class CardHandle {

    private GameView gameView;

    public CardHandle (GameView gameView) {
        this.gameView = gameView;


        gameView.copperButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().clear();
                gameView.resourceButton.getStyleClass().add("copperBig");

            }
        });

        gameView.copperButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().add("invisible");

            }
        });

        gameView.silverButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().clear();
                gameView.resourceButton.getStyleClass().add("silverBig");

            }
        });

        gameView.silverButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().add("invisible");

            }
        });

        gameView.goldButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().clear();
                gameView.resourceButton.getStyleClass().add("goldBig");

            }
        });

        gameView.goldButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().add("invisible");

            }
        });


        gameView.estateButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().clear();
                gameView.resourceButton.getStyleClass().add("estateBig");

            }
        });

        gameView.estateButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().add("invisible");

            }
        });


        gameView.duchyButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().clear();
                gameView.resourceButton.getStyleClass().add("duchyBig");

            }
        });

        gameView.duchyButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().add("invisible");

            }
        });


        gameView.estateButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().clear();
                gameView.resourceButton.getStyleClass().add("estateBig");

            }
        });

        gameView.estateButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().add("invisible");

            }
        });

        gameView.provinceButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().clear();
                gameView.resourceButton.getStyleClass().add("provinceBig");

            }
        });

        gameView.provinceButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.resourceButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton4.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton4.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton5.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton5.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton6.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton6.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton7.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton7.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton8.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton8.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton9.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton9.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });

        gameView.actionButton10.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().clear();
                gameView.actionButton.getStyleClass().add("villageBig");

            }
        });

        gameView.actionButton10.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameView.actionButton.getStyleClass().add("invisible");

            }
        });



    }


}
