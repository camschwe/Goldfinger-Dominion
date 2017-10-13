package Game.Cards;

/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class PointCard extends Card{

    String cardName;
    int cost;
    int value;

    public PointCard(String cardName, int cost, int value) {
        this.cardName = cardName;
        this.cost = cost;
        this.value = value;
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
