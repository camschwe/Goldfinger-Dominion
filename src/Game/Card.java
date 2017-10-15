package Game;

import Game.Player;

/**
 * Created by camillo.schweizer on 13.10.2017.
 */
public class Card {

    String cardName;
    int cost, value;

    public Card(String cardName, int cost, int value) {
        this.cost = cost;
        this.cardName = cardName;
        this.value = value;
    }

    public static void village(Player player){
        player.draw(1);
        player.setActions((player.getActions() +1));

    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
