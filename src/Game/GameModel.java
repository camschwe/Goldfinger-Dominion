package Game;

import Client_Server.Client.Client;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by camillo.schweizer on 07.10.2017.
 *
 * Klasse zum Spiel mit allen globalen Parametern sowie Initialisierung der Kartenobjekte und der Arrays für
 * die Feldkarten
 */
public class GameModel {

    int playerCount;
    private Player player;
    private int turn;
    private ArrayList<Card> pointCards;
    private ArrayList<Card> moneyCards;
    private ArrayList<Card> actionCards;
    private ArrayList<String> playerList;

    public GameModel(Client client) {

        this.playerList = (client.getPlayers());

        this.player = new Player(client.getClientName());
        this.turn = 1;

        /**
         * Initialisierung der Kartenobjekte für das Spielerdeck und Verteilung auf das Array. Es wird jeweils eine Karte
         * generiert und die dem Button hinterlegt.
         */

        for(int i = 0; i < 3 ; i++) {
            Card estateHand = new Card("estate","point", 2,1);
            player.getDrawDeck().add(estateHand);
        }

        for(int i = 0; i< 7; i++) {
            Card copperHand = new Card("copper","money", 0, 1);
            player.getDrawDeck().add(copperHand);
        }

        Collections.shuffle(player.getDrawDeck());
        player.draw(5);

        /**
         * Initialisierung der Kartenobjekte für das Spielfeld und Verteilung auf das Array. Es wird jeweils eine Karte
         * generiert und die dem Button hinterlegt.
         */

        Card copper = new Card("copper","money", 0, 1);
        Card silver = new Card("silver","money", 3, 2);
        Card gold = new Card("gold","money", 6, 3);
        Card estate = new Card("estate","point", 2, 1 );
        Card duchy = new Card("duchy","point", 5, 3);
        Card province = new Card("province","point", 8, 6);

        pointCards = new ArrayList<>();
        Collections.addAll(pointCards, province, duchy, estate);
        moneyCards = new ArrayList<>();
        Collections.addAll(moneyCards,gold, silver, copper);

        actionCards = new ArrayList<>();

        Card moneylender = new Card("moneylender", "action", 4,0);
        actionCards.add(moneylender);
        Card fair = new Card("fair","action", 5, 0 );
        actionCards.add(fair);
        Card market = new Card("market","action", 5, 0);
        actionCards.add(market);
        Card laboratory = new Card("laboratory","action", 5, 0);
        actionCards.add(laboratory);
        Card adventurer = new Card("adventurer", "action", 6,0);
        actionCards.add(adventurer);
        Card village = new Card("village","action", 3, 0 );
        actionCards.add(village);
        Card lumberjack = new Card("lumberjack","action", 3, 0);
        actionCards.add(lumberjack);
        Card chancellor = new Card("chancellor", "action", 3,0);
        actionCards.add(chancellor);
        Card magpie = new Card("magpie", "action", 4,0);
        actionCards.add(magpie);
        Card smithy = new Card("smithy","action", 4, 0);
        actionCards.add(smithy);
    }

    /**
     * Methode um die Anzahl der Geldkarten auf diem Spielfeld anhand der Spieler festzulegen.
     */

    public int moneyButtonAmount(int index){
        switch (index) {
            case 2:
                return 60-(7*playerList.size());
            case 1:
                return 40;
            case 0:
                return 30;
            default:
                return 10;
        }
    }

    /**
     * Methode um die Anzahl der Punktekarten auf diem Spielfeld anhand der Spieler festzulegen.
     */

    public int pointButtonAmount(){
        if(playerList.size()<3){
            return 8;
        }else{
            return 12;
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Card> getPointCards() {
        return pointCards;
    }

    public ArrayList<Card> getMoneyCards() {
        return moneyCards;
    }

    public Player getPlayer(){
        return player;
    }

    public ArrayList<Card> getActionCards() {
        return actionCards;
    }

    public ArrayList<String> getPlayerList() {
        return playerList;
    }

}
