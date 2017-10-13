package Game.Cards;

import Game.GameModel;
import Game.Player;

/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class ActionCard extends Card {

    String cardName;
    int cost;

    public ActionCard(String cardName, int cost){
        this.cardName = cardName;
        this.cost = cost;

    }

    public void village(Player player, GameModel gameModel){
        player.draw(1);
        gameModel.setAction(gameModel.getAction() +1);

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
}
