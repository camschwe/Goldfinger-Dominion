package Client_Server;

import Game.GameButton;
import Game.Player;

import java.io.Serializable;

/**
 * Created by camillo.schweizer on 23.10.2017.
 */
public class Container implements Serializable {
    private Player player;
    private GameButton gameButton;
    private int action;


    //Action 0 = gespielt, Action 1 = gekauft
    public Container(Player player, GameButton gameButton, int action){
        this.player = player;
        this.gameButton = gameButton;
        this.action = action;

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

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
