package Client_Server.Server;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server extends Thread {

    private static final int PORT = 22022;
    private static HashSet<String> names = new HashSet<String>();
    private static HashSet<ObjectOutputStream> writers = new HashSet<ObjectOutputStream>();
    private String name;
    private ObjectInputStream objInput;
    private ObjectOutputStream objOutput;
    private Socket server;
    ServerSocket socket;


    public Server(){

        try {
            socket = new ServerSocket(PORT);
            System.out.println("Server waiting");

            server = socket.accept();

            while (true) {
                this.start();
            }

        } catch (Exception e) {

        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {

            }
        }
    }

    public void run(){
        try {
            this.objInput = new ObjectInputStream(server.getInputStream());

            this.objOutput = new ObjectOutputStream(server.getOutputStream());

            if (objInput.readObject().getClass().equals(Type.String)) {
                while (true) {
                    name = (String) objInput.readObject();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            System.out.println("Name added: " + name);
                            break;
                        }
                    }
                }
                writers.add(objOutput);
            }

            while (true){
                for (ObjectOutputStream objSend : writers) {
                    objSend.writeObject(name);
                    System.out.println("" + objInput.readObject());
                }
            }

            //objInput.close();
            //objOutput.close();
        } catch (Exception e) {

        }
    }

    public static void main(String[] args){
        new Server();
    }


}
