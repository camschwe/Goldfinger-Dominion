package Client_Server.Client;


import Client_Server.Chat.Message;
import Game.HandCardController;
import Lobby.LobbyController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Benjamin Probst on 01.10.2017.
 * last edit: 12.10.2017
 **/

public class Client extends Thread {
    private static final int PORT = 22022;
    private static ObjectInputStream objInput;
    private static ObjectOutputStream objOutput;
    private static String serverAdresse;
    private static Socket serverSocket;
    private static Thread thread;
    private static boolean running = true;
    private String clientName;
    private boolean valid = false;
    private boolean checked = false;
    private LobbyController lobbyController;
    private HandCardController handCardController;
    private int actualController;
    private static String color;

    public Client(String serverAdresse, String clientName){
        try {
            this.serverAdresse = serverAdresse;
            this.clientName = clientName;
            serverSocket = new Socket(this.serverAdresse, PORT);
            objOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            objInput = new ObjectInputStream(serverSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run(){
        while (running){
            Object o;
            try {
                o = objInput.readObject();
                doSomething(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //doSomething(o);
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public String getClientName(){
        return this.clientName;
    }

    public void doSomething(Object o){
        if (o instanceof Message){
            Message message = (Message) o;
            switch (message.getType()){
                case 1:
                    actualizeChat(message);
                    break;
                case 3:
                    switch (message.getMessage()){
                        case "valid":
                            valid = true;
                            checked = true;
                            this.color = message.getColor();
                            break;
                        case "invalid":
                            valid = false;
                            checked = true;
                            break;
                        case "add":
                            actualizePlayers(message);
                            break;
                    }
                case 4:
                    //startGame();
                    break;
            }
        }
    }

    private void actualizePlayers(Message message) {
        if (actualController == 1){
            // lobbyController.getLobbyView().playersTxtArea.appendText(message.getClientName() + System.getProperty("line.separator"));
        }
    }

    public void actualizeChat(Message message) {
        if (actualController == 1){
            //message = new Message(message.getType(), message.getClientName(), message.getMessage(), this.color);
            //lobbyController.getLobbyView().getChatWindow().actualizeTextArea(message.getFullMessage());
            lobbyController.getLobbyView().getChatWindow().actualizeChatFlow(message);
        }else if (actualController == 2){
            // gameController.getGameModel().actualizeChatWindow(message);
        }
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isChecked(){
        return checked;
    }

    public void resetChecked(){
        checked = false;
    }

    public void sendObject(Object o){
        try {
            objOutput.writeObject(o);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void setLobbyController (LobbyController lobbyController){
        this.lobbyController = lobbyController;
        actualController = 1;
    }

    public void setGameController (HandCardController handCardController){
        this.handCardController = handCardController;
        actualController = 2;
    }

    public void stopClient(){
        running = false;
    }
}
