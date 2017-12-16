package Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class Player implements Serializable, Comparable{

    /**
     * Spielerklassen mit Arrays für alle Kartendecks sowie Methoden für den Kauf und das ausspielen von Karten
     */
    private ArrayList<Card> handCards;
    private ArrayList<Card> putDeck;
    private ArrayList<Card> drawDeck;
    private ArrayList<Card> playDeck;
    private boolean yourTurn, actionPhase, buyPhase, turnEnded;
    private int actions, buys, money, points;
    private String playerName;

    public Player(String playerName) {
        this.playerName = playerName;
        this.handCards = new ArrayList<>();
        this.putDeck = new ArrayList<>();
        this.drawDeck = new ArrayList<>();
        this.playDeck = new ArrayList<>();
        this.actionPhase = false;
        this.buyPhase = true;
        this.actions = 1;
        this.buys = 1;
        this.money = 0;
        this.points = 3;
        this.yourTurn = false;
        this.turnEnded = false;
    }

    /**
     * Zieht die Anzahl von angegebener Karten vom Nachziehstapel. Insofern auf diesem nicht ausreichend Karten zur
     * Verfügung stehen, wird eine supportmethode aufgerufen, um die Decks auszutauschen und die Karten zu mischeln.
     */

    public void draw(int cards){
        if(this.drawDeck.size() == 0 && this.putDeck.size() > 0){
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

        if(this.drawDeck.size() + this.putDeck.size() != 0){
            for(int i = cards-1; i>= 0;i--) {
                this.handCards.add(this.drawDeck.get(i));
                this.drawDeck.remove(i);
            }
        }
    }

    /**
     * Supportmethode um die Karten von einem Deck in das andere zu kopieren. Es wird die Karte auf der letzten Position
     * genommen und bei dem anderen Deck bei der letzten Position wieder eingefügt.
     */

    public void changeDecks (ArrayList<Card> removeArray,ArrayList<Card> addArray){
            for(int i = removeArray.size()-1; i>=0 ; i--){
                addArray.add(removeArray.get(i));
                removeArray.remove(i);
        }
    }

    /**
     * Karte wird von der Hand auf dem Spielstapel gelegt und dort bei der letzten Position eingefügt
     */

    public void addPlayCard(Card card){
        this.changeCardPlace(card, this.handCards, this.playDeck);
    }

    /**
     * Karte wird von einem Array in einn anderen Array verschoben und dort bei der letzten Position eingefügt
     */

    public void changeCardPlace(Card card, ArrayList<Card> removeArray,ArrayList<Card> addArray ){

        for(int i =0; i< removeArray.size();i++){
            if(removeArray.get(i).equals(card)){
                addArray.add(card);
                removeArray.remove(card);
            }
        }
    }

    /**
     * Suppportmethode für den Abschluss eines Spielzugs - Alle Handkarten sowie die Ausgespielten Karten werden
     * auf den Ablagestapel gelegt.
     */

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
     * Karte wird gekauft und auf den Spielstapel gelegt. Zudem wird der Geldbetrag vom Spieler abgebucht. Wenn der
     * Spieler keine Käufe mehr übrig hat, wird Methode zum beenden des Spielzugs aufgerufen.
     */

    public void buyCard(Card card){
        this.putDeck.add(card);
        this.money -= card.getCost();
        this.buys -= 1;
        if(card.getType().equals("point")){
            this.points += card.getValue();
        }
        if(this.buys < 1){
            endPhase();
        }
    }

    /**
     * Schliesst die aktuelle Phase ab und insofern es sich um die Kaufphase handelt, wird Methode zum beenden des
     * Spielzugs aufgerufen
     */

    public void endPhase(){
        if(this.actionPhase) {
            this.buyPhase = true;
            this.actionPhase = false;
        }else{
            endTurn();
            }
    }

    /**
     * Schliesst den aktuellen Spielzug ab und ruft die Methoden auf, um die Karten auf den Ablagestapel zu legen
     * sowie 5 Handkarten für den neuen Zug zu ziehen
     */

    public void endTurn(){
        this.money = 0;
        this.buys = 1;
        this.actions = 1;
        dropCards();
        draw(5);
        phaseChanger();
        this.turnEnded = true;
        this.yourTurn = false;
    }

    /**
     * Supportmethode um die Aktionsphase zu überspringen, insofern sich keine entsprechende Karte in der Hand befindet
     */

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
     * Supportmethode um zu überprüfen, ob sich eine Aktionskarte in der Hand befindet und gibt einen Boolean zurück
     */

    public boolean handCardActionChecker(){
        for (Card card : handCards) {
            if (card.getType().equals("action")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Supportmethode um alle Geldkarten in der Hnad auszuspielen und fügt dem Spieler den entsprechenden Geldbetrag
     * hinzu. Retourniert wird eine temporäre Arrayliste für die weitere Bearbeitung
     */

    public ArrayList<Card> playAllMoneyCards(){
        for (Card card : this.handCards){
            if(card.getType().equals("money")){
                this.money += card.getValue();
            }
        }

        int i = this.handCards.size() -1;
        ArrayList<Card> tempList = new ArrayList<>();

        while(i >= 0) {
            if(handCards.get(i).getType().equals("money")){
                tempList.add(handCards.get(i));
                this.changeCardPlace(handCards.get(i), this.handCards, this.playDeck);
            }
            i--;
        }
        return tempList;
    }


    public ArrayList<Card> getPlayDeck() {
        return playDeck;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public ArrayList<Card> getPutDeck() {
        return putDeck;
    }

    public ArrayList<Card> getDrawDeck() {
        return drawDeck;
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

    public boolean isBuyPhase() {
        return buyPhase;
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
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPoints() {
        return points;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isTurnEnded() {
        return turnEnded;
    }

    public void setTurnEnded(boolean turnEnded) {
        this.turnEnded = turnEnded;
    }

    @Override public String toString(){
        System.out.println("Name: "+this.getPlayerName());
        System.out.println("Money: "+this.getMoney());
        System.out.println("Points: "+this.getPoints());
        System.out.print("Handcards :");
        for(Card card : handCards){
            System.out.print(card.getName()+":");

        }

        System.out.print("\nPlayed Cards :");
        for(Card card : playDeck){
            System.out.print(card.getName()+":");
        }

        return("\n\n\n");

    }

    @Override
    public int compareTo(Object o) {
        Player p = (Player) o;
        return p.getPoints() - this.points;
    }

}
