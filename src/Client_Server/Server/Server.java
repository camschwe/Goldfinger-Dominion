package Client_Server.Server;

import Client_Server.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * Created by Benjamin Probst on 01.10.2017.
 * last edit: 11.10.2017
 **/

public class Server extends Thread{
    private static final int PORT = 22022;
    private static final int MAXSPIELER = 4;
    private Thread thread;
    private final String threadName = "Server";
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private static HashSet<String> names = new HashSet<String>();
    private static String clientName;
    private static HashSet<ObjectOutputStream> outputs = new HashSet<ObjectOutputStream>();
    private boolean running = true;
    private ObjectInputStream objInput = null;
    private ObjectOutputStream objOutput = null;
    private Object input;


    public Server(){

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            running = true;
            try {
                socket = serverSocket.accept();
                System.out.println("Client verbunden");
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                objInput = new ObjectInputStream(socket.getInputStream());
                objOutput = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("In- und Output erstellt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (running) {
                try {
                    input = objInput.readObject();
                    System.out.println(input + " Objekttyp: " + input.getClass());
                    if (input instanceof Message) {

                        Message message = (Message) input;
                        clientName = message.getClientName();

                        switch (message.getType()){

                            case 0:
                                synchronized (names) {
                                    if (!names.contains(clientName)) {
                                      names.add(clientName);
                                      outputs.add(objOutput);
                                      input = new Message(3, clientName, "valid");
                                      objOutput.writeObject(input);
                                      System.out.println("Benutzer hinzugefügt: " + ((Message) input).getFullMessage());
                                    }else {
                                        input = new Message(3, clientName, "invalid");
                                        objOutput.writeObject(input);
                                    }
                                }
                                break;

                            case 1:
                                sendToAll();
                                break;

                            case 2:
                                this.removeClient();
                                break;
                        }

                    } else {
                        sendToAll();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR");
                    stopServer();
                }
            }
        }

    }
    public void start(){
        for (int i = 0; i < MAXSPIELER; i++){

                thread = new Thread(this, threadName);
                thread.start();

        }
    }

    public void stopServer(){
        running = false;
        this.removeClient();
        try {
            objInput.close();
            objOutput.close();
        } catch (Exception e) {
            System.out.println("Error closing Input and Output Streams");
        }
    }

    public void removeClient(){
        names.remove(clientName);
        outputs.remove(objOutput);
    }
    public void sendToAll() throws IOException {
        synchronized (outputs){
            for (ObjectOutputStream output : outputs) {
                if (output != null) {
                    output.writeObject(input);
                }
            }
        }
    }

}