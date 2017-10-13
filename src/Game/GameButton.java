package Game;

import Game.Cards.*;
import javafx.scene.control.Button;

/**
 * Created by camillo.schweizer on 13.10.2017.
 */
public class GameButton extends Button {
    final int ACTIONCOUNTER =10,POINTCOUNTER2 = 8, POINTCOUNTER4 = 12;
    int cardCounter;
    ActionCard actionCard;
    MoneyCard moneyCard;
    PointCard pointCard;



    public GameButton(ActionCard actionCard) {
        this.cardCounter = this.ACTIONCOUNTER;
        this.actionCard = actionCard;

    }

    public GameButton(MoneyCard moneyCard) {
        this.moneyCard = moneyCard;

    }

    public GameButton(PointCard pointCard, int playerCount) {
        if (playerCount <3)
            this.cardCounter = this.POINTCOUNTER2;
        else this.cardCounter = this.POINTCOUNTER4;

        this.pointCard = pointCard;




    }
}
