package Client_Server;

import java.io.Serializable;

/**
 * Created by Benjamin Probst on 14.10.2017.
 * last edit: 14.10.2017
 **/

public class Message implements Serializable {
    /**
     * die Variable type speichert die möglichen Arten von Nachrichten:
     * 0 = neuer Spieler, 1 = eine Nachricht, 2 = Spieler meldet sich ab, 3 = Spielername validation
     * Die Message benötigt immer einen Typ, einen Client Namen und die Nachricht.
     */
    private int type;
    private String message;
    private String clientName;

    public Message (int type,String clientName, String message){
        this.type = type;
        this.clientName = clientName;
        this.message = message;
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

    public String getClientName() {
        return clientName;
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
