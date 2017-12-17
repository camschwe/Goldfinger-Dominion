package Client_Server.Server;

import Client_Server.Chat.Message;
import Client_Server.GameObject;
import Game.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Benjamin Probst on 01.10.2017.
 **/

public class Server extends Thread{
    private static final int PORT = 22022;
    private static ServerSocket serverSocket;
    private static HashSet<String> players = new HashSet<String>();
    private static HashSet<ObjectOutputStream> outputs = new HashSet<ObjectOutputStream>();
    private static ArrayList<String> colors = new ArrayList<>();
    private static ArrayList<String> gamePlayers = new ArrayList<>();
    private static ArrayList<Player> endPlayers = new ArrayList<>();
    private static int actualPlayer = 0;
    private static boolean gameStarted = false;
    private static boolean gameEnded = false;
    private static int round = 1;
    private static int maxRounds = Integer.MAX_VALUE;
    private static boolean roundLimit = false;
    private static int provinces = 8;

    public Server() throws Exception{
        colors.add("-fx-fill: red");
        colors.add("-fx-fill: green");
        colors.add("-fx-fill: blue");
        colors.add("-fx-fill: mediumvioletred");
        serverSocket = new ServerSocket(PORT);
        try {
            while (true){
                Handler handler = new Handler(serverSocket.accept());
                handler.start();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class Handler extends Thread{
        private Socket socket;
        private String name;
        private ObjectInputStream objInput;
        private ObjectOutputStream objOutput;

        public Handler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                objInput = new ObjectInputStream(socket.getInputStream());
                objOutput = new ObjectOutputStream(socket.getOutputStream());
                Object o = new Object();

                outputs.add(objOutput);

                while (true) {
                    try {
                        try {
                            o = objInput.readObject();
                        } catch (Exception e){
                        }
                        if (o == null) {
                            return;
                        }
                        if (o instanceof Message){
                            Message message = (Message) o;
                            name = message.getClientName();
                            switch (message.getType()){
                                case 0:
                                    addPlayer(message);
                                    break;

                                case 1:
                                    sendMessageToAll(message);
                                    break;

                                case 2:
                                    colors.add(message.getColor());
                                    removeClient();
                                    break;
                                case 4:
                                    if (message.getMessage().equals("start")) {
                                        gameEnded = false;
                                        setGamePlayers();
                                        sendMessageToAll(message);
                                        Collections.shuffle(gamePlayers);
                                        nextPlayer();
                                        gameStarted = true;
                                    } else if (message.getMessage().equals("EndGame")){
                                        gameEnded = true;
                                        sendMessageToAll(message);
                                        sendToAll(endPlayers);
                                    }
                                    break;
                                case 6:
                                    nextPlayer();
                                    Collections.sort(endPlayers);
                                    break;
                                case 7:
                                    setRoundLimit(message);
                                    break;
                            }
                        }else if (o instanceof GameObject){
                            GameObject gameObject = (GameObject) o;
                            if (gameObject.getCard().getName().equals("province")){
                                provinces--;
                            }
                            actualizeEndPlayers(gameObject.getPlayer());
                            sendToAll(gameObject);
                        } else if (o instanceof Player){
                            synchronized (endPlayers){
                                endPlayers.add((Player) o);
                            }
                        }
                    } catch (Exception e) {
                        //removeClient();
                        break;
                    }
                }
            } catch (Exception e){

            } finally {
                if (objOutput != null){
                    outputs.remove(objOutput);
                }
                try {
                    socket.close();
                } catch (Exception e){

                }
            }

        }

        // Hinzufügen des neuen Spielers
        private void addPlayer(Message message) throws IOException {
            synchronized (players) {
                if (!players.contains(message.getClientName())) {
                    players.add(name);
                    outputs.add(objOutput);
                    Message send;
                    if (!colors.isEmpty()) {
                        Collections.shuffle(colors);
                        send = new Message(3, name, "valid", colors.get(0));
                        colors.remove(0);
                    } else {
                        send = new Message(3, name, "valid", getRandomColor());
                    }
                    objOutput.writeObject(send);
                    sendPlayerList();
                    sendMessageToAll(new Message(3, name, "actualize"));
                    if (gameStarted){
                        objOutput.writeObject(new Message(4, name, "running"));
                    }
                }else {
                    Message send = new Message(3, name, "invalid");
                    objOutput.writeObject(send);
                }
            }
        }

        // Actualize Player Object list
        private void actualizeEndPlayers(Player receivedPlayer){
            if (endPlayers.size() == 0){
                endPlayers.add(receivedPlayer);
            } else {
                boolean playerActualized = false;
                for (int i = 0; i < endPlayers.size(); i++) {
                    if (endPlayers.get(i).getPlayerName().equals(receivedPlayer.getPlayerName())) {
                        endPlayers.set(i, receivedPlayer);
                        playerActualized = true;
                    }
                }
                if (!playerActualized) {
                    endPlayers.add(receivedPlayer);
                }
            }
        }

        // Senden der Spielerliste an alle Spieler
        private void sendPlayerList() throws IOException {
            for (String name : players){
                sendMessageToAll(new Message(3, name, "add"));
            }
        }

        // Wenn ein Client die Verbindung trennt, soll er auch aus der Liste entfernt werden
        private void removeClient() throws IOException {
            sendMessageToAll(new Message(1, "Server", "Client " + name + " has left the Server.", "-fx-fill: black"));
            int rem = 10;
            for (int i = 0; i < gamePlayers.size(); i++){
                if (gamePlayers.get(i).equals(name)){
                    rem = i;
                }
            }
            if (rem != 10){
                gamePlayers.remove(rem);
                if (actualPlayer == gamePlayers.size()){
                    actualPlayer = 0;
                }
            }

            if (actualPlayer == 0) {
                if (rem == gamePlayers.size()){
                    nextPlayer();
                }
            } else {
                if (rem == actualPlayer-1){
                    actualPlayer = actualPlayer -1;
                    nextPlayer();
                    if (actualPlayer < gamePlayers.size() - 1) {
                        actualPlayer += 1;
                    } else {
                        actualPlayer = 0;
                        round++;
                    }
                }
            }
            players.remove(name);
            outputs.remove(objOutput);
            objInput.close();
            objOutput.close();
            sendPlayerList();
        }

        // Übermitteln eines Objekts an alle Spieler
        private void sendToAll(Object o) throws IOException {
            for (ObjectOutputStream output : outputs){
                if (output != null){
                    output.writeObject(o);
                    output.reset();
                }

            }
        }

        // Übermitteln einer Message an alle Spieler
        private void sendMessageToAll(Message message) throws IOException {
            for (ObjectOutputStream output : outputs){
                if (output != null){
                    try {
                        output.writeObject(message);
                        output.reset();
                    } catch (Exception e){
                        
                    }
                }
            }
        }

        // Festlegung der Spieler im Spiel
        private void setGamePlayers(){
            for (String player : players) {
                gamePlayers.add(player);
            }
        }

        // Der nächste Spieler wird gesendet
        private void nextPlayer() throws IOException {
            if (!gameEnded) {
                if (roundLimit && round == maxRounds + 1){
                    gameEnded = true;
                    sendMessageToAll(new Message(4, "Round limit reached", "EndGame"));
                    sendToAll(endPlayers);
                } else {

                    sendMessageToAll(new Message(5, gamePlayers.get(actualPlayer), "turn"));
                    sendMessageToAll(new Message(7, gamePlayers.get(actualPlayer), Integer.toString(round)));
                    if (actualPlayer < gamePlayers.size() - 1) {
                        actualPlayer += 1;
                    } else {
                        actualPlayer = 0;
                        round++;
                    }
                    for (Player player : endPlayers) {
                        if (player.getPlayerName().equals(gamePlayers.get(actualPlayer))) {
                            sendToAll(player);
                        }
                    }
                }
            }
        }

        /**
         * Generator für zufällige Farbe für Spieler
         * Format: -fx-fill: #XXXXXX
         */
        private String getRandomColor(){
            String color = "-fx-fill: #";
            for (int i = 1; i < 3; i++){
                int red = ThreadLocalRandom.current().nextInt(0, 16);
                int green = ThreadLocalRandom.current().nextInt(0, 16);
                int blue = ThreadLocalRandom.current().nextInt(0, 16);
                color += Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
            }
            return color;
        }

        public void setRoundLimit(Message message) {
            maxRounds = Integer.parseInt(message.getMessage());
            roundLimit = true;
        }
    }
}