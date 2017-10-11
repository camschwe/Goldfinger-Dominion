package Client_Server.Server;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server extends Thread{
    private static final int PORT = 22022;
    private static final int MAXSPIELER = 4;
    private Thread thread;
    private final String threadName = "Server";
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    //private String[] names = new String[4];
    //private ObjectOutputStream[] outputs = new ObjectOutputStream[4];
    private static HashSet<String> names = new HashSet<String>();
    private static String clientName;
    private static HashSet<ObjectOutputStream> outputs = new HashSet<ObjectOutputStream>();
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

                        clientName = (String) input;

                        synchronized (names){
                            if (!names.contains(clientName)){
                                names.add(clientName);
                                outputs.add(objOutput);
                                System.out.println("Benutzer hinzugefügt");
                            }
                        }

                        /**boolean vorhanden = false;
                        for (String name : names) {
                            if (name != null) {
                                if (name.equals(input)) {
                                    vorhanden = true;
                                    System.out.println("Name bereits vergeben");
                                }
                            }
                        }
                        if (!vorhanden) {
                            synchronized (names) {
                                clientName = (String) input;
                                names.add(clientName);
                                outputs.add(objOutput);
                                System.out.println("Benutzer hinzugefügt");
                            }
                        }**/
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
        for (int i = 0; i < MAXSPIELER; i++){

                thread = new Thread(this, threadName);
                thread.start();

        }
    }

    public void stopServer(){
        running = false;
        //names[0] = null;
        names.remove(clientName);
        //outputs[0] = null;
        outputs.remove(objOutput);
        try {
            objInput.close();
            objOutput.close();
        } catch (Exception e) {
            System.out.println("Error closing Input and Output Streams");
        }
    }

}