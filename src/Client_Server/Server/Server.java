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
    private String[] names = new String[4];
    private ObjectOutputStream[] outputs = new ObjectOutputStream[4];
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
                    Object input = objInput.readObject();
                    System.out.println(input + " Objekttyp: " + input.getClass());
                    if (input instanceof String) {
                        boolean vorhanden = false;
                        for (String name : names) {
                            if (name != null) {
                                if (name.equals(input)) {
                                    vorhanden = true;
                                    System.out.println("Name bereits vergeben");
                                }
                            }
                        }
                        if (!vorhanden) {
                            names[0] = (String) input;
                            System.out.println("User");
                            outputs[0] = objOutput;
                            System.out.println("Benutzer hinzugefügt");
                        }
                    } else {
                        for (ObjectOutputStream output : outputs) {
                            if (output != null) {
                                output.writeObject(input);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ERROR");
                    stopServer();
                }
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
        names[0] = null;
        outputs[0] = null;
    }

}