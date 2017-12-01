package End;

import Game.Card;
import Game.Player;

/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class EndModel {

    public int  cardCounter(String cardName, Player player) {
        int count = 0;

        for (Card card : player.getPlayDeck()) {
            if (card.getName().equals(cardName)) {
                count++;
            }
        }

        for (Card card : player.getDrawDeck()) {
            if (card.getName().equals(cardName)) {
                count++;
            }
        }

        for (Card card : player.getHandCards()) {
            if (card.getName().equals(cardName)) {
                count++;
            }
        }

        for (Card card : player.getPutDeck()) {
            if (card.getName().equals(cardName)) {
                count++;
            }
        }

        return count;

    }

}
