package Game;

import java.io.Serializable;

import static sun.audio.AudioPlayer.player;

/**
 * Created by camillo.schweizer on 13.10.2017.
 * Generiert einfache Kartenobjekte mit einem String für den Namen sowie dem Typen. Zudem sind Methoden vorhanden,
 * um die Aktion der jeweiligen Karte auszuführen.
 */
public class Card implements Serializable {

    String cardName, type;
    int cost, value;

    public Card(String cardName, String type, int cost, int value) {
        this.cost = cost;
        this.type = type;
        this.cardName = cardName;
        this.value = value;
    }

    public static Card cardCopy(Card card){
        Card coppyCard = new Card(card.getName(),card.getType(), card.getCost(), card.getValue());
        return coppyCard;

    }

    public String getName() {
        return cardName;
    }

    /**
     * Methoden für das ausspielen von Aktionskarten
     */


    public void village(Player player){
        player.draw(1);
        player.setActions(player.getActions() + 1);
        actionSupport(this, player);
    }

    public void fair(Player player){
        player.setMoney(player.getMoney()+2);
        player.setBuys(player.getBuys()+1);
        player.setActions(player.getActions() + 1);
        actionSupport(this, player);
    }

    public void smithy(Player player){
        player.draw(3);
        player.setActions(player.getActions()- 1);
        actionSupport(this, player);
    }

    public void market(Player player){
        player.draw(1);
        player.setBuys(player.getBuys()+1);
        player.setMoney(player.getMoney()+1);
        actionSupport(this, player);
    }

    public void laboratory(Player player){
        player.draw(2);
        actionSupport(this, player);
    }

    public void lumberjack(Player player){
        player.setMoney(player.getMoney()+2);
        player.setActions(player.getActions() - 1);
        player.setBuys(player.getBuys()+1);
        actionSupport(this, player);
    }

    public void adventurer(Player player){
        int counter = 0;


        while(counter<2){
            if(player.getDrawDeck().size()<1){
                player.changeDecks(player.getPutDeck(), player.getDrawDeck());
            }
            if(player.getDrawDeck().get(0).getType().equals("money")){
                player.draw(1);
                counter++;
            }else{
                player.getPlayDeck().add(player.getDrawDeck().get(0));
                player.getDrawDeck().remove(0);

            }
        }
        player.setActions(player.getActions()- 1);
        actionSupport(this, player);
    }

    public void moneylender(Player player){

        for(int i= 0; i<player.getHandCards().size(); i++){
            if(player.getHandCards().get(i).getType().equals("money") && player.getHandCards().get(i).getValue()==1){
                player.getHandCards().remove(i);
                player.setMoney(player.getMoney()+3);
                i = player.getHandCards().size();
            }
        }
        player.setActions(player.getActions()- 1);
        actionSupport(this, player);
    }

    public void chancellor(Player player){
        player.setMoney(player.getMoney()+2);
        player.setActions(player.getActions()- 1);
        for(int i = player.getDrawDeck().size()-1; i>=0 ; i--){
            player.getPutDeck().add(player.getDrawDeck().get(i));
            player.getDrawDeck().remove(i);
        }
        actionSupport(this, player);
    }

    public boolean magpie (Player player){
        player.draw(1);

        if(player.getDrawDeck().size() == 0){
            player.changeDecks(player.getPutDeck(), player.getDrawDeck());
        }

        if(player.getDrawDeck().get(0).getType().equals("money")){
            player.draw(1);
            actionSupport(this, player);
            return false;
        }else{
            actionSupport(this, player);
            return true;
        }
    }

    /**
     * Supportmethode um die Phase abzuschliessen, wenn der Spieler keine Aktionen mehr überig hat.
     */


    public void actionSupport(Card card, Player player) {
        player.addPlayCard(card);
        if (player.getActions() < 1 || !player.handCardActionChecker()) {
            player.endPhase();
        }
    }

    /**
     * Methode für das ausspielen einer Geldkarte - Fügt den Wert der Geldkarte dem Spieler hinzu und entfernt die
     * Karte aus der Hand
     */

    public void playMoneyCard(Player player){
        player.setMoney(player.getMoney()+ this.getValue());
        player.addPlayCard(this);
        if(player.getBuys() < 1) {
            player.endPhase();
        }
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
