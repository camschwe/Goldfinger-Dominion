package Client_Server.Server;

import Client_Server.Chat.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Benjamin Probst on 01.10.2017.
 * last edit: 11.10.2017
 **/

public class ServerTest extends Thread{
    private static final int PORT = 22022;
    private static ServerSocket serverSocket = null;
    //private Socket socket = null;
    private static HashSet<String> players = new HashSet<String>();
    private static HashSet<ObjectOutputStream> outputs = new HashSet<ObjectOutputStream>();

    public ServerTest() throws Exception{
        System.out.println("Server started.");
        serverSocket = new ServerSocket(PORT);
        try {
            while (true){
                new Handler(serverSocket.accept()).start();
            }
        } catch (Exception e){

        }
    }

    private static class Handler extends Thread{
        private Socket socket;
        private String name;
        private ObjectInputStream obInput;
        private ObjectOutputStream objOutput;

        public Handler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                obInput = new ObjectInputStream(socket.getInputStream());
                objOutput = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Client connected");

                outputs.add(objOutput);

                while (true) {
                    try {
                        Object o = obInput.readObject();
                        if (o == null) {
                            return;
                        }
                        for (ObjectOutputStream output : outputs){
                            output.writeObject(o);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR");
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
    }
}