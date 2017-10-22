package Client_Server.Client;


import Client_Server.Chat.Message;
import Game.GameController;
import Game.HandCardController;
import Lobby.LobbyController;
import javafx.application.Platform;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Benjamin Probst on 01.10.2017.
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
    private static String color;
    private static ArrayList<String> players = new ArrayList<>();
    private static boolean reset = true;

    public Client(String serverAdresse, String clientName){
        try {
            Client.serverAdresse = serverAdresse;
            this.clientName = clientName;
            serverSocket = new Socket(Client.serverAdresse, PORT);
            objOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            objInput = new ObjectInputStream(serverSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getColor() {
        return color;
    }

    public void run(){
        while (running){
            Object o;
            try {
                o = objInput.readObject();
                handleObject(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public String getClientName(){
        return this.clientName;
    }

    public void handleObject(Object o){
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
                            color = message.getColor();
                            break;
                        case "invalid":
                            valid = false;
                            checked = true;
                            break;
                        case "add":
                            addPlayers(message);
                            break;
                        case "actualize":
                            actualizePlayers();
                            reset = true;
                    }
                case 4:
                    //startGame();
                    break;
            }
        }
    }

    // die Clientseitige Spielerliste wird erstellt und aktualisiert
    private void addPlayers(Message message){
        if (reset) {
            players.clear();
            reset = false;
        }
        players.add(message.getClientName());
        System.out.println("Player added: " + message.getClientName());
    }

    // Aktualisierung der Spielerliste in der LobbyView
    public void actualizePlayers() {
        System.out.println("Start setting labels");
        int i = 0;

        if (actualController == 1){
            System.out.println("go");
            for (String player: players){
                final int iCopy = i;
                Platform.runLater(() -> {
                    lobbyController.getLobbyView().getPlayers().get(iCopy).setText(player);
                    lobbyController.getLobbyView().getPlayers().get(iCopy).setVisible(true);
                });
                i++;
            }
        }
        reset = true;
    }

    // Aktualisieren der Chat Nachrichten
    public void actualizeChat(Message message) {
        if (actualController == 1){
            lobbyController.getLobbyView().getChatWindow().actualizeChatFlow(message);
        }else if (actualController == 2){
            gameController.getGameView().getChatWindow().actualizeChatFlow(message);
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

    // Sendet ein Objekt an den Server
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
        running = false;
    }
}
