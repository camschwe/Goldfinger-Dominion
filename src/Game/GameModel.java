package Game;

import Game.Cards.ActionCard;
import Game.Cards.MoneyCard;
import Game.Cards.PointCard;

import java.util.Collections;


/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class GameModel {
    boolean yourTurn;
    int playerCount;
    int action = 1;
    int buy = 1;
    Player player;



    public GameModel(int playerCount) {

        this.player = new Player();

        for(int i = 0; i < 3 ; i++) {

            PointCard village = new PointCard("estate", 3,1);
            player.getDrawStaple().add(village);
        }

        for(int i = 0; i< 7; i++) {
            MoneyCard copper = new MoneyCard("copper", 2, 1);
            player.getDrawStaple().add(copper);
        }

        Collections.shuffle(player.getDrawStaple());

        player.draw(5);





    }

    public int  getPlayerCount(){
        return this.playerCount;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public int getAction() {
        return action;
    }

    public int getBuy() {
        return buy;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public Player getPlayer(){
        return player;
    }
}
