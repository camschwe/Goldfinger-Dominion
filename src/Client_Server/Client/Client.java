package Client_Server.Client;


import Client_Server.Chat.Message;
import Client_Server.GameObject;
import Game.GameController;
import Game.HandCardController;
import Game.Player;
import Lobby.LobbyController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;

import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import static javafx.scene.media.AudioClip.INDEFINITE;

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
    private boolean turn = false;
    private boolean gameStarted = false;
    private boolean isServer = false;
    private boolean connected = false;
    private boolean failure = false;
    private String resolution;
    private boolean musicActivated = true;
    private static AudioClip audioClip;

    /**
     * Konstruktor
     * @param serverAdresse Adresse des Servers
     * @param clientName Name des Clients
     * @param resolution Auflösung
     * @param audioClip für Hintergrundmusik
     * @param musicActivated für allgemein Musik
     */
    public Client(String serverAdresse, String clientName, String resolution, AudioClip audioClip, boolean musicActivated){
        try {
            this.audioClip = audioClip;
            this.musicActivated = musicActivated;
            Client.serverAdresse = serverAdresse;
            this.clientName = clientName;
            this.resolution = resolution;
            serverSocket = new Socket(Client.serverAdresse, PORT);
            objOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            objInput = new ObjectInputStream(serverSocket.getInputStream());
            connected = true;
        } catch (Exception e) {
            //e.printStackTrace();
            failure = true;
        }

    }

    /**
     * Der Client hört auf Objekte vom Server und ruft eine Handler Methode auf
     */
    public void run(){
        while (running) {
            Object o = null;
            try {
                try {
                    o = objInput.readObject();
                } catch (EOFException e) {
                    e.printStackTrace();
                    objInput.close();
                    serverSocket.close();
                } catch (SocketException e){

                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    /**
     * Handler Methode um das Objekt vom Server zu verarbeiten
     * @param o Objekt
     */
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
                    break;
                case 4:
                    if (message.getMessage().equals("start")){
                        Platform.runLater(() -> lobbyController.startGame());
                    } else if (message.getMessage().equals("running")){
                        gameStarted = true;
                    } else if (message.getMessage().equals("EndGame")){
                        gameController.endGame();
                    }
                    break;
                case 5:
                    turn = message.getClientName().equals(this.clientName);
                    while (actualController != 2){
                        /**Waiting for gameController setting up**/
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    gameController.newTurn(turn);
                    if (gameStarted){
                        gameController.getFieldCardController().getSpectatorController().changePlayerCards();
                    }
                    break;
                case 7:
                    gameController.changeTurnLabels(message.getClientName(), Integer.parseInt(message.getMessage()));
            }
        }
        if(o instanceof GameObject){
            GameObject gameObject = (GameObject) o;
            gameController.otherPlayerChecker(gameObject);
        }
        if (o instanceof ArrayList){
            gameController.endView((ArrayList<Player>) o);
        }
        if (o instanceof Player){
            if (actualController == 2 && !((Player) o).getPlayerName().equals(this.clientName)){
                gameController.player2PointUpdate((Player)o);
            }
        }
    }

    /**
     * die Clientseitige Spielerliste wird erstellt und aktualisiert
     * @param message Nachricht mit Spielern vom Server
     */
    private void addPlayers(Message message){
        if (players.size() <= 4) {
            if (reset) {
                players.clear();
                reset = false;
            }
            players.add(message.getClientName());
        }
    }

    /**
     * Aktualisierung der Spielerliste in der LobbyView
     */
    public void actualizePlayers() {
        int i = 0;

        if (actualController == 1){
            for (String player: players){
                final int iCopy = i;
                Platform.runLater(() -> {
                    lobbyController.getLobbyView().getPlayers().get(iCopy).setText("- " + player);
                    lobbyController.getLobbyView().getPlayers().get(iCopy).setVisible(true);
                });
                i++;
                if (i >= 4){
                    break;
                }
            }
        }
        reset = true;
    }

    /**
     * Aktualisieren der Chat Nachrichten
     * @param message Nachricht
     */
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

    /**
     * Sendet ein Objekt an den Server
     * @param o Objekt an den Server
     */
    public void sendObject(Object o){
        try {
            if(o instanceof GameObject) {
                GameObject gameObject = (GameObject) o;
                gameObject.setColor(color);
                objOutput.reset();
                objOutput.writeObject(gameObject);

            } else {
                objOutput.writeObject(o);
            }
            objOutput.reset();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLobbyController (LobbyController lobbyController){
        this.lobbyController = lobbyController;
        actualController = 1;
    }

    public void setGameController (GameController gameController){
        this.gameController = gameController;
        actualController = 2;
        gameController.getGameModel().getPlayer().setYourTurn(turn);
    }

    public void setServer() {
        isServer = true;
    }

    public boolean isServer(){
        return isServer;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Methode für das beenden des Clients
     */
    public void stopClient(){
        sendObject(new Message(2, this.clientName, "left", color));
        try {
            objOutput.close();
            objInput.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = false;
    }

    public static void startBackground(){
        /**
         * Methode um ein Audio File abzuspielen.
         * Kopiert von: https://stackoverflow.com/questions/31784698/javafx-background-thread-task-should-play-music-in-a-loop-as-background-thread
         * @param fileName
         */
        final Task task = new Task() {

            @Override
            protected Object call() throws Exception {
                int s = INDEFINITE;
                audioClip = new AudioClip(getClass().getResource("/Sounds/background.wav").toExternalForm());
                audioClip.setVolume(0.09);
                audioClip.setCycleCount(s);
                audioClip.play();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void stopMusic(){
        this.musicActivated = false;
        audioClip.stop();
    }

    public boolean getMusicActivated(){
        return musicActivated;
    }

    public void setMusicActivated(boolean music){
        this.musicActivated = music;
    }

    public static String getColor(){
        return Client.color;
    }

    public boolean isConnected(){
        return connected;
    }

    public boolean isFailure() {
        return failure;
    }

    public static ArrayList<String> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<String> players) {
        Client.players = players;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
