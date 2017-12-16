package Client_Server;

import Game.Card;
import Game.Player;

import java.io.Serializable;

/**
 * Created by camillo.schweizer on 23.10.2017.
 *
 * Container für die Übermittlung von Spielzügen. Es werden die Karte, der entsprechende Spieler sowie auch die Aktion
 * (0 = gespielt, Action 1 = gekauft) übermittelt
 */
public class GameObject implements Serializable {
    private Player player;
    private Card card;
    private int action;
    private String color;

    public GameObject(Player player, Card card, int action){
        this.player = player;
        this.card = card;
        this.action = action;
        this.color = "!valid";
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Card getCard() {
        return card;
    }

    public int getAction() {
        return action;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
