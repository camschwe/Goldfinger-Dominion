package Game;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class GameModel {

    /**
     * Klasse zum Spiel mit allen globalen Parametern sowie Initialisierung der Kartenobjekte sowie der Arrays für
     * die Feldkarten
     */

    int playerCount;
    Player player;
    private ArrayList<Card> pointCards;
    private ArrayList<Card> moneyCards;
    private ArrayList<Card> actionCards;

    public GameModel(int playerCount) {

        this.player = new Player();


        /**
         * Initialisierung der Kartenobjekte
         */


        for(int i = 0; i < 3 ; i++) {
            Card estateHand = new Card("estate","point", 2,1);
            player.getDrawDeck().add(estateHand);
        }

        for(int i = 0; i< 7; i++) {
            Card copperHand = new Card("copper","money", 0, 1);
            player.getDrawDeck().add(copperHand);
        }

        Collections.shuffle(player.getDrawDeck());

        player.draw(5);

        Card copper = new Card("copper","money", 0, 1);
        Card silver = new Card("silver","money", 3, 2);
        Card gold = new Card("gold","money", 6, 3);
        Card estate = new Card("estate","point", 2, 1 );
        Card duchy = new Card("duchy","point", 5, 3);
        Card province = new Card("province","point", 8, 6);

        pointCards = new ArrayList<>();
        Collections.addAll(pointCards, province, duchy, estate);
        moneyCards = new ArrayList<>();
        Collections.addAll(moneyCards,gold, silver, copper);

        actionCards = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            Card village = new Card("village","action", 3, 0 );
            actionCards.add(village);
        }
    }



    public int  getPlayerCount(){
        return this.playerCount;
    }


    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Card> getPointCards() {
        return pointCards;
    }

    public void setPointCards(ArrayList<Card> pointCards) {
        this.pointCards = pointCards;
    }

    public ArrayList<Card> getMoneyCards() {
        return moneyCards;
    }

    public void setMoneyCards(ArrayList<Card> moneyCards) {
        this.moneyCards = moneyCards;
    }

    public Player getPlayer(){
        return player;
    }

    public ArrayList<Card> getActionCards() {
        return actionCards;
    }

    public void setActionCards(ArrayList<Card> actionCards) {
        this.actionCards = actionCards;
    }
}
