package Game;

import java.io.Serializable;

/**
 * Created by camillo.schweizer on 13.10.2017.
 */
public class Card implements Serializable {

    /**
     * Generiert einfache Kartenobjekte mit einem String für den Namen sowie dem Typen
     */

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

    //Führt Aktionskarte Village aus
    public void village(Player player){
        player.draw(1);
        player.setActions(player.getActions() + 1);
        this.actionSupport(this, player);

    }

    public void fair(Player player){
        player.setMoney(player.getMoney()+2);
        player.setBuys(player.getBuys()+1);
        player.setActions(player.getActions() + 1);
        this.actionSupport(this, player);
    }

    public void smithy(Player player){
        player.draw(3);
        player.setActions(player.getActions()- 1);
        this.actionSupport(this, player);
    }

    public void market(Player player){
        player.draw(1);
        player.setActions(player.getActions()+1);
        player.setMoney(player.getMoney()+1);
        this.actionSupport(this, player);
    }

    public void laboratory(Player player){
        player.draw(2);
        this.actionSupport(this, player);
    }

    public void lumberjack(Player player){
        player.setMoney(player.getMoney()+2);
        player.setActions(player.getActions() - 1);
        player.setBuys(player.getBuys()+1);
        this.actionSupport(this, player);
    }


    public void actionSupport(Card card, Player player) {
        player.addPlayCard(card);
        if (player.getActions() < 1 || !player.handCardActionChecker()) {
            player.endPhase();
        }
    }

    /**
     * Methode für das ausspielen einer Geldkarte
     */

    //Fügt den Wert der Geldkarte dem Spieler hinzu und entfernt die Karte aus der Hand
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
