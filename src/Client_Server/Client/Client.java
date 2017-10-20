package Client_Server.Client;


import Client_Server.Chat.Message;
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
    private static ArrayList<String> players = new ArrayList<>();
    private static boolean reset = true;

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

    public static String getColor() {
        return color;
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
                            addPlayers(message);
                            break;
                        case "end":
                            actualizePlayers();
                            reset = true;
                    }
                case 4:
                    //startGame();
                    break;
            }
        }
    }

    private void addPlayers(Message message){
        if (reset) {
            players.clear();
            reset = false;
        }
        players.add(message.getClientName());
        System.out.println("Player added: " + message.getClientName());
    }

    private void actualizePlayers() {
        System.out.println("Start setting labels");
        int i = 0;
        if (actualController == 1){
            System.out.println("go");
            //ArrayList<Label>  labels = lobbyController.getLobbyView().getPlayers();
            for (javafx.scene.control.Label label : lobbyController.getLobbyView().getPlayers()){
                if (i != -1) {
                    if (players.get(i) != null && label.getText().equals("unknown")) {
                        System.out.println("setting Labels");
                        String name = players.get(i);
                        System.out.println(name);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                label.setText(name);
                                label.setVisible(true);
                            }
                        });
                    }
                    if (i < players.size()-1){
                        i++;
                    }else {
                        i = -1;
                    }
                }
            }
        }
        /**if (actualController == 2){
            //ArrayList<Label>  labels = lobbyController.getLobbyView().getPlayers();
            for (javafx.scene.control.Label label : handCardController.getLobbyView().getPlayers()){
                if (players.get(i) != null) {
                    label.setText(players.get(i));
                    label.setVisible(true);
                    i++;
                }
            }
        }**/

        reset = true;
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
