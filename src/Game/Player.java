package Game;

import Game.Cards.Card;

import java.util.ArrayList;

/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class Player {

    private ArrayList<Card> handCards;
    private ArrayList<Card> putStaple;
    private ArrayList<Card> drawStaple;

    public Player() {
        handCards = new ArrayList<>();
        putStaple = new ArrayList<>();
        drawStaple = new ArrayList<>();




    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public ArrayList<Card> getPutStaple() {
        return putStaple;
    }

    public void setPutStaple(ArrayList<Card> putStaple) {
        this.putStaple = putStaple;
    }

    public ArrayList<Card> getDrawStaple() {
        return drawStaple;
    }

    public void setDrawStaple(ArrayList<Card> drawStaple) {
        this.drawStaple = drawStaple;
    }

    public void setHandCards(ArrayList<Card> handCards) {
        this.handCards = handCards;
    }


    //ToDo: ADD METHOD
    public void draw(int cards){
        for(int i = 0; i< 5;i++){
            handCards.add(i,drawStaple.get(i));
            drawStaple.remove(i);
        }

    }
}
