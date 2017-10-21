package Game;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class Player {

    /**
     * Spielerklassen mit Arrays für das GUI sowie Methoden für den Kauf und das ausspielen von Karten
     */
    private ArrayList<Card> handCards;
    private ArrayList<Card> putDeck;
    private ArrayList<Card> drawDeck;
    private ArrayList<Card> playDeck;
    private boolean yourTurn, actionPhase, buyPhase;
    private int actions, buys, money, points;

    public Player() {
        this.handCards = new ArrayList<>();
        this.putDeck = new ArrayList<>();
        this.drawDeck = new ArrayList<>();
        this.playDeck = new ArrayList<>();
        this.actionPhase = false;
        this.buyPhase = true;
        this.actions = 1;
        this.buys = 1;
        this.money = 0;
        this.points = 0;
        this.yourTurn = true;
    }

    /**
     * Methoden für das ausspielen von Aktionskarten
     */

    //Führt Aktionskarte Village aus
    public void village(Card card){
        this.draw(1);
        this.actions += 1;
        this.addPlayCard(card);
        if(this.actions < 1 || !this.handCardActionChecker()) {
            this.endPhase();
        }
    }

    /**
     * Methoden um die verschiedenen Arrays des Spielers zu managen. Beispielsweise für den Zug von einer beliebigen Anzahl
     * von Spielkarten und wechselt den Ablagestapel mit dem Ziehstapel, wenn keine Karten mehr vorhanden sind
     */

    //Zieht Anzahl angegebener Karten vom Nachziehstapel
    public void draw(int cards){
        if(this.drawDeck.size() == 0){
            this.changeDecks(this.putDeck, this.drawDeck);
            Collections.shuffle(this.drawDeck);
        }
        if(this.drawDeck.size() < cards){
            for(int i = this.drawDeck.size()-1 ; i>=0 ;i--){
                this.handCards.add(this.drawDeck.get(i));
                this.drawDeck.remove(i);
                cards--;
            }
            this.changeDecks(this.putDeck, this.drawDeck);
            Collections.shuffle(this.drawDeck);
        }
        for(int i = cards-1; i>= 0;i--){
            this.handCards.add(this.drawDeck.get(i));
            this.drawDeck.remove(i);
        }
    }

    //Karten werden von einem Deck in ein anderes übernommen
    public void changeDecks (ArrayList<Card> removeArray,ArrayList<Card> addArray){
        for(int i = removeArray.size()-1; i>=0 ; i--){
            addArray.add(removeArray.get(i));
            removeArray.remove(i);
        }

    }

    //Legt eine Handkarte auf den Ablagestapel
    public void dropCard(Card card) {
        this.changeCardPlace(card, this.handCards, this.putDeck);
    }

    //Legt eine Handkarte auf den Spielstapel
    public void addPlayCard(Card card){
        this.changeCardPlace(card, this.handCards, this.playDeck);
    }

    //Wechselt die Karten von einem Deck in ein anderes
    public void changeCardPlace(Card card, ArrayList<Card> removeArray,ArrayList<Card> addArray ){
        int i = 0;
        boolean checker = false;
        while (removeArray.size() - 1 >= i || !checker) {
            if (card.equals(removeArray.get(i))) {
                addArray.add(removeArray.get(i));
                removeArray.remove(i);
                checker = true;
            }
            i++;
        }
    }

    //Spielerlarten auf dem Feld sowie aus der Hand werden auf den Ablagestapel gelegt
    public void dropCards(){
        for(int i = this.handCards.size()-1; i >= 0;i--){
            this.playDeck.add(this.handCards.get(i));
            this.handCards.remove(i);
        }
        for(int i = this.playDeck.size()-1; i >= 0;i--){
            this.putDeck.add(this.playDeck.get(i));
            this.playDeck.remove(i);
        }
    }

    /**
     * Methode für das ausspielen einer Geldkarte
     */

    //Fügt den Wert der Geldkarte dem Spieler hinzu und entfernt die Karte aus der Hand
    public void playMoneyCard(Card card){
        this.money += card.getValue();
        this.addPlayCard(card);
        if(this.buys < 1) {
            this.endPhase();
        }
    }

    /**
     * Methode für das kaufen einer Karte
     */

    //karte wird gekauft und auf den Spielstapel gelegt. Zudem wird der Geldbetrag vom Spieler abgebucht
    public void buyCard(Card card){
        this.putDeck.add(card);
        this.money -= card.getCost();
        this.buys -= 1;
        if(this.buys < 1){
            this.endPhase();
        }
    }

    /**
     * Methoden für das Zug- bzw Phasenmanagement
     */

    //Schliesst die aktuelle Phase ab
    public void endPhase(){
        if(this.actionPhase) {
            this.buyPhase = true;
            this.actionPhase = false;
        }else{
            this.endTurn();
            }
    }

    //TODO: Change TO OTHER PLAYER
    //Schliesst den aktuellen Zug Ab und zieht 5 neue Karten
    public void endTurn(){
        this.money = 0;
        this.buys = 1;
        this.actions = 1;

        this.dropCards();
        this.draw(5);
        this.phaseChanger();
    }

    //überspringt die Aktionsphase, insofern keine Aktionskarte in der Hand
    public void phaseChanger(){
        if(this.handCardActionChecker()) {
            this.buyPhase = false;
            this.actionPhase = true;
        }else{
            this.actionPhase = false;
            this.buyPhase = true;
        }
    }

    /**
     * Supportmethode um die Handkarten zu überprüfen
     */

    //überprüft ob sich eine Aktionskarte in der Hand befindet
    public boolean handCardActionChecker(){
        boolean checker = false;
        for(int i = this.handCards.size()-1; i >= 0;i--){
            if(this.handCards.get(i).getType().equals("action")){
                checker = true;            }
        }
        return checker;
    }




    public ArrayList<Card> getPlayDeck() {
        return playDeck;
    }

    public void setPlayDeck(ArrayList<Card> playDeck) {
        this.playDeck = playDeck;
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

    public boolean isBuyPhase() {
        return buyPhase;
    }

    public void setBuyPhase(boolean buyPhase) {
        this.buyPhase = buyPhase;
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
