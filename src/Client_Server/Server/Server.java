package Client_Server.Server;

import Client_Server.Chat.Message;
import Client_Server.GameObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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
    private static int actualPlayer = 0;

    public Server() throws Exception{
        colors.add("-fx-fill: red");
        colors.add("-fx-fill: green");
        colors.add("-fx-fill: blue");
        colors.add("-fx-fill: yellow");
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
                            System.out.println("Error reading Object");
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
                                    setGamePlayers();
                                    sendMessageToAll(message);
                                    Collections.shuffle(gamePlayers);
                                    nextPlayer();
                                    break;
                                case 6:
                                    nextPlayer();
                                    break;
                            }
                        }else if (o instanceof GameObject){
                            GameObject gameObject = (GameObject) o;
                            System.out.println("Server Objekt: " + gameObject);
                            sendToAll(gameObject);

                            //System.out.println("Server ");
                            //for(int i = 0 ; i < gameObject.getPlayer().getPlayDeck().size(); i++)
                            //    System.out.print(" "+gameObject.getPlayer().getPlayDeck().get(i).getName());

                            //System.out.println("\n");
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR");
                        removeClient();
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
                    Message send = new Message(3, name, "valid", colors.get(0));
                    objOutput.writeObject(send);
                    sendPlayerList();
                    sendMessageToAll(new Message(3, name, "actualize"));
                    colors.remove(0);
                }else {
                    Message send = new Message(3, name, "invalid");
                    objOutput.writeObject(send);
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
            players.remove(name);
            outputs.remove(objOutput);
            sendPlayerList();
        }

        // Übermitteln eines Objekts an alle Spieler
        private void sendToAll(Object o) throws IOException {
            System.out.println("Server sending Object: " + o);
            for (ObjectOutputStream output : outputs){
                if (output != null){
                    output.writeObject(o);
                    output.flush();
                }

            }
        }

        // Übermitteln einer Message an alle Spieler
        private void sendMessageToAll(Message message) throws IOException {
            for (ObjectOutputStream output : outputs){
                if (output != null){
                    output.writeObject(message);
                    output.flush();
                }

            }
        }

        private void setGamePlayers(){
            for (String player : players) {
                gamePlayers.add(player);
            }
        }

        // Der nächste Spieler wird gesendet
        private void nextPlayer() throws IOException {
            sendMessageToAll(new Message(5, gamePlayers.get(actualPlayer), "turn"));
            if (actualPlayer < gamePlayers.size()-1) {
                actualPlayer += 1;
            } else {
                actualPlayer = 0;
            }
        }
    }
}