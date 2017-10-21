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
        this.buyPase = false;
        this.actions = 1;
        this.buys = 1;
        this.money = 0;
        this.points = 0;
        this.yourTurn = true;

    }

    public void village(Card card){
        this.draw(1);
        this.setActions((this.getActions() +1));
        dropCard(card);

    }

    public void draw(int cards){

        if(this.drawDeck.size() == 0){
            this.changeDecks();
        }

        if(this.drawDeck.size() < cards){
            for(int i = this.drawDeck.size()-1 ; i>=0 ;i--){
                this.handCards.add(this.drawDeck.get(0));
                this.drawDeck.remove(0);
                cards--;
            }

            this.changeDecks();
        }

        for(int i = cards-1; i>= 0;i--){
            this.handCards.add(this.drawDeck.get(0));
            this.drawDeck.remove(0);
        }



    }

    public void changeDecks (){
        for(int i = this.putDeck.size()-1; i>=0 ; i--){
            this.drawDeck.add(this.putDeck.get(0));
            this.putDeck.remove(0);
            Collections.shuffle(drawDeck);
        }

    }

    public void playMoneyCard(Card card){

        this.setMoney(this.getMoney()+ card.getValue());
        dropCard(card);


    }

    public void dropCard(Card card) {
        int i = 0;
        boolean checker = false;
        while (this.getHandCards().size() - 1 >= i || !checker) {
            if (card.equals(this.getHandCards().get(i))) {
                this.getPutDeck().add(this.getHandCards().get(i));
                this.getHandCards().remove(i);
                checker = true;
            }
            i++;

        }


    }


    public void buyCard(Card card){

        this.getPutDeck().add(card);
        this.setMoney(this.getMoney() - card.getCost());
        this.setBuys(this.getBuys() -1);
        if(this.getBuys() < 1){
            this.setBuyPase(false);
            this.endTurn();
        }
    }

    //TODO: Change TO OTHER PLAYER
    public void endPhase(){
        if(this.isActionPhase()) {
            this.setBuyPase(true);
            this.setActionPhase(false);
        }else{
            this.endTurn();

            }


    }

    public void endTurn(){
        this.money = 0;
        for(int i = this.getHandCards().size()-1; i >= 0;i--){
            this.putDeck.add(this.handCards.get(0));
            this.handCards.remove(0);
        }

        this.setBuys(1);
        this.setActions(1);
        this.setBuyPase(false);
        this.setActionPhase(true);
        this.draw(5);


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


}
