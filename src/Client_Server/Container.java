package Client_Server;

import Game.GameButton;
import Game.Player;

/**
 * Created by camillo.schweizer on 23.10.2017.
 */
public class Container {
    private Player player;
    private GameButton gameButton;
    boolean played;
    boolean bought;

    public Container(Player player, GameButton gameButton, boolean played, boolean bought){
        this.player = player;
        this.gameButton = gameButton;
        this.played = played;
        this.bought = bought;

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameButton getGameButton() {
        return gameButton;
    }

    public void setGameButton(GameButton gameButton) {
        this.gameButton = gameButton;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
