package Game;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class Player {

    private ArrayList<Card> handCards;
    private ArrayList<Card> putDeck;
    private ArrayList<Card> drawDeck;
    private boolean yourTurn, actionPhase, buyPase;
    private int actions, buys, money, points;


    public Player() {
        this.handCards = new ArrayList<>();
        this.putDeck = new ArrayList<>();
        this.drawDeck = new ArrayList<>();
        this.actionPhase = true;
        this.buyPase = true;
        this.actions = 1;
        this.buys = 1;
        this.money = 0;
        this.points = 0;
        this.yourTurn = true;




    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public ArrayList<Card> getPutDeck() {
        return putDeck;
    }

    public void setPutDeck(ArrayList<Card> putDeck) {
        this.putDeck = putDeck;
    }

    public ArrayList<Card> getDrawDeck() {
        return drawDeck;
    }

    public void setDrawDeck(ArrayList<Card> drawDeck) {
        this.drawDeck = drawDeck;
    }

    public void setHandCards(ArrayList<Card> handCards) {
        this.handCards = handCards;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public boolean isActionPhase() {
        return actionPhase;
    }

    public void setActionPhase(boolean actionPhase) {
        this.actionPhase = actionPhase;
    }

    public boolean isBuyPase() {
        return buyPase;
    }

    public void setBuyPase(boolean buyPase) {
        this.buyPase = buyPase;
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public int getBuys() {
        return buys;
    }

    public void setBuys(int buys) {
        this.buys = buys;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void draw(int cards){

        if(handCards.size() < cards){
            for(int i = 0; i< handCards.size();i++){
                this.handCards.add(i,this.drawDeck.get(i));
                this.drawDeck.remove(i);
            }
            cards -= this.handCards.size();
            changeDecks();
        }

        for(int i = 0; i< cards;i++){
            this.handCards.add(i,this.drawDeck.get(i));
            this.drawDeck.remove(i);
        }

        if(this.drawDeck.size() == 0){
            changeDecks();
        }

    }

    public void changeDecks (){
        for(int i = 0; i< this.putDeck.size(); i++){
            this.drawDeck.add(i, this.putDeck.get(i));
            this.putDeck.remove(i);
            Collections.shuffle(drawDeck);
        }


    }
}
