package Client_Server.Client;


import Client_Server.Chat.Message;
import Game.GameController;
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
    private GameController gameController;
    private int actualController;

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
            Object o = null;
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
                    if (message.getMessage().equals("invalid")){
                        this.valid = false;
                    } else {
                        this.valid = true;
                    }
                    checked = true;
                    break;
                case 4:
                    //startGame();
                    break;
            }
        }
    }

    private void actualizeChat(Message message) {
        if (actualController == 1){
            lobbyController.getLobbyView().getChatWindow().actualizeTextArea(message.getFullMessage());
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

    public void setGameController (GameController gameController){
        this.gameController = gameController;
        actualController = 2;
    }

    public void stopClient(){
        this.running = false;
    }
}
