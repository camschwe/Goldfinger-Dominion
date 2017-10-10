package Client_Server.Server;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    static final int PORT = 22022;
    private Thread thread;
    private final String threadName = "Server";
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<ObjectOutputStream> outputs = new ArrayList<>();
    private boolean running = true;
    private ObjectInputStream objInput = null;
    private ObjectOutputStream objOutput = null;


    public Server(){

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }**/
    }

    public void run() {
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

        while (running){
            try {
                Object input = objInput.readObject();
                System.out.println(input + " Objekttyp: " + input.getClass());
                if (input.getClass().equals(Type.String)){
                    for (String name : names) {
                        if (name != null){
                            if (!name.equals((String)input)){
                                names.add((String) input);
                                outputs.add(objOutput);
                                System.out.println("Benutzer hinzugefügt");
                            }else {
                                System.out.println("Name bereits vergeben");
                            }
                        }
                    }
                } else {
                    for (ObjectOutputStream output : outputs){
                        output.writeObject(input);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }

    }
    public void start(){
        if (thread == null){
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public void stopServer(){
        running = false;
    }

}