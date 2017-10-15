package Game;

import javafx.scene.control.Button;

/**
 * Created by camillo.schweizer on 13.10.2017.
 */
public class GameButton extends Button {
    Card card;


    public GameButton(Card card) {
        this.card = card;

    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
