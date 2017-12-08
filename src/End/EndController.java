package End;

import Game.GameController;
import Game.GameModel;
import Game.Player;
import Localisation.Localisator;
import Login.EndView;
import javafx.scene.control.Label;


import java.util.ArrayList;

/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class EndController {
    private Localisator localisator;
    private EndView endView;
    private EndModel endModel;
    private ArrayList<Player> playerList;
    private GameController gameController;


    public EndController(EndModel endModel, EndView endView, Localisator localisator, ArrayList<Player> playerList, GameModel gameModel, GameController gameController) {
        this.endView = endView;
        this.endModel = endModel;
        this.localisator = localisator;
        this.playerList = playerList;
        this.gameController = gameController;


        endView.leaveButton.setOnAction(event -> {
            System.exit(0);
        });


        if(playerList.size()>3 && endView.getResolution().equals("720p")){
            endView.scene.getStylesheets().clear();
            endView.scene.getStylesheets().add("../Stylesheets/EndStylesMini.css");
        }

        if(playerList.size()>3 && endView.getResolution().equals("1080p")){
            endView.scene.getStylesheets().clear();
            endView.scene.getStylesheets().add("../Stylesheets/EndStylesSmall.css");
        }

        if(playerList.get(0).getPlayerName().equals(gameModel.getPlayer().getPlayerName())){
            endView.resultLabel.setText(localisator.getResourceBundle().getString("won"));
            gameController.playSound("MONSTER_KILL");
        }else{
            gameController.playSound("DENIED");
        }

        for(int i= 0; i<playerList.size(); i++) {

            int y= i;
            int j= 0;


            if(i>1){
                y-=2;
            }

            if(i>1){
                j=1;
            }



            Label playerLabel = new Label(playerList.get(i).getPlayerName());
            playerLabel.getStyleClass().add("playerLabel");
            Label estateLabel = new Label(localisator.getResourceBundle().getString("estate"));
            Label duchyLabel = new Label(localisator.getResourceBundle().getString("duchy"));
            Label provinceLabel = new Label(localisator.getResourceBundle().getString("province"));

            Label playerPoint = new Label(""+playerList.get(i).getPoints());
            playerPoint.getStyleClass().add("playerLabel");
            Label estateAmount = new Label(""+endModel.cardCounter("estate", playerList.get(i)));
            Label duchyAmount = new Label(""+endModel.cardCounter("duchy", playerList.get(i)));
            Label provinceAmount = new Label(""+endModel.cardCounter("province", playerList.get(i)));

            Label estatePoints = new Label(""+endModel.cardCounter("estate", playerList.get(i))*1);
            Label duchyPoints = new Label(""+endModel.cardCounter("duchy", playerList.get(i))*3);
            Label provicePonts = new Label(""+endModel.cardCounter("province", playerList.get(i))*6);

            endView.gridPane.add(playerLabel, 3*y+0, 5*j+1);
            endView.gridPane.add(estateLabel, 3*y+0, 5*j+2);
            endView.gridPane.add(duchyLabel, 3*y+0, 5*j+3);
            endView.gridPane.add(provinceLabel, 3*y+0, 5*j+4);

            endView.gridPane.add(estateAmount, 3*y+1, 5*j+2);
            endView.gridPane.add(duchyAmount, 3*y+1, 5*j+3);
            endView.gridPane.add(provinceAmount, 3*y+1, 5*j+4);

            endView.gridPane.add(playerPoint, 3*y+2, 5*j+1);
            endView.gridPane.add(estatePoints, 3*y+2, 5*j+2);
            endView.gridPane.add(duchyPoints, 3*y+2, 5*j+3);
            endView.gridPane.add(provicePonts, 3*y+2, 5*j+4);

        }

    }


}




