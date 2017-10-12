package Client_Server.Client;


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

    public Client(String serverAdresse){
        try {
            this.serverAdresse = serverAdresse;
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            doSomething(o);
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public static void doSomething(Object o){

    }

    public void sendObject(Object o){
        try {
            objOutput.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopClient(){
        this.running = false;
    }
}
