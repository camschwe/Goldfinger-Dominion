package Game;

import Game.Player;

/**
 * Created by camillo.schweizer on 13.10.2017.
 */
public class Card {

    /**
     * Generiert einfache Kartenobjekte mit einem String für den Namen sowie dem Typen
     */

    String cardName, type;
    int cost, value;

    public Card(String cardName, String type, int cost, int value) {
        this.cost = cost;
        this.type = type;
        this.cardName = cardName;
        this.value = value;
    }

    public static Card cardCopy(Card card){
        Card coppyCard = new Card(card.getCardName(),card.getType(), card.getCost(), card.getValue());
        return coppyCard;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
