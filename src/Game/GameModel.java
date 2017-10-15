package Game;

import java.util.Collections;


/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class GameModel {

    int playerCount;
    Player player;



    public GameModel(int playerCount) {

        this.player = new Player();


        for(int i = 0; i < 3 ; i++) {

            Card village = new Card("estate", 1,1);
            player.getDrawDeck().add(village);
        }

        for(int i = 0; i< 7; i++) {
            Card copper = new Card("copper", 0, 1);
            player.getDrawDeck().add(copper);
        }

        Collections.shuffle(player.getDrawDeck());

        player.draw(5);





    }

    public int  getPlayerCount(){
        return this.playerCount;
    }


    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }


    public Player getPlayer(){
        return player;
    }
}
