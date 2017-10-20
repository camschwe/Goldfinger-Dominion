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
 **/

public class Server extends Thread{
    private static final int PORT = 22022;
    private static ServerSocket serverSocket;
    private static HashSet<String> players = new HashSet<String>();
    private static HashSet<ObjectOutputStream> outputs = new HashSet<ObjectOutputStream>();
    private static ArrayList<String> colors = new ArrayList<>();

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
        private ObjectInputStream obInput;
        private ObjectOutputStream objOutput;

        public Handler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                obInput = new ObjectInputStream(socket.getInputStream());
                objOutput = new ObjectOutputStream(socket.getOutputStream());

                outputs.add(objOutput);

                while (true) {
                    try {
                        Object o = obInput.readObject();
                        if (o == null) {
                            return;
                        }
                        if (o instanceof Message){
                            Message message = (Message) o;
                            name = message.getClientName();
                            switch (message.getType()){
                                case 0:
                                    synchronized (players) {
                                        if (!players.contains(message.getClientName())) {
                                            players.add(name);
                                            outputs.add(objOutput);
                                            Message send = new Message(3, name, "valid", colors.get(0));
                                            objOutput.writeObject(send);
                                            sendPlayerList();
                                            sendMessageToAll(new Message(3, name, "end"));
                                            colors.remove(0);
                                        }else {
                                            Message send = new Message(3, name, "invalid");
                                            objOutput.writeObject(send);
                                        }
                                    }
                                    break;

                                case 1:
                                    sendMessageToAll(message);
                                    break;

                                case 2:
                                    removeClient();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR");
                        players.remove(name);
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

        private void sendPlayerList() throws IOException {
            for (String name : players){
                sendMessageToAll(new Message(3, name, "add"));
            }
        }

        private void removeClient() {
            players.remove(name);
            outputs.remove(objOutput);
        }

        private void sendToAll(Object o) throws IOException {
            for (ObjectOutputStream output : outputs){
                if (output != null){
                    output.writeObject(o);
                }

            }
        }

        private void sendMessageToAll(Message message) throws IOException {
            for (ObjectOutputStream output : outputs){
                if (output != null){
                    output.writeObject(message);
                }

            }
        }
    }
}