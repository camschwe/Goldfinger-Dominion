package Client_Server.Chat;

import java.io.Serializable;

/**
 * Created by Benjamin Probst on 14.10.2017.
 **/

public class Message implements Serializable {
    /**
     * die Variable type speichert die möglichen Arten von Nachrichten:
     * 0 = neuer Spieler, 1 = eine Nachricht, 2 = Spieler meldet sich ab, 3 = Spielername validation, 4 = Game started, 5 = aktueller Spieler, 6 = Turn ended
     *
     * Die Variable color speichert als String die zu verwendende Farbe als css Ausdruck
     *
     * Die Message benötigt immer einen Typ, einen Client Namen, die Nachricht und optional die Farbe.
     */
    private int type;
    private String message;
    private String clientName;
    private String color;

    // Konstruktor
    public Message (int type,String clientName, String message){
        this.type = type;
        this.clientName = clientName;
        this.message = message;
    }

    // Konstruktor mit Farbe
    public Message (int type,String clientName, String message, String color){
        this.type = type;
        this.clientName = clientName;
        this.message = message;
        this.color = color;
    }

    public int getType(){
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getFullMessage(){
        String fullMessage = (this.clientName + ": " + toString());
        return fullMessage;
    }

    public String getColor() {
        return color;
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public String toString() {
        return ("Typ: " + this.type + ", Benutzer: " + this.clientName + ", Nachricht: " + this.getMessage() + ((color != null) ? " Farbe: " + this.color : "" ));
    }
}
