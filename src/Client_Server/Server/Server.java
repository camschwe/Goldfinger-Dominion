package Client_Server.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 22022;
    private static final int MAX_PLAYERS = 4;
    private static final String[] players = new String[MAX_PLAYERS];
    private static int i = 0;
    private static ObjectOutputStream[] writers = new ObjectOutputStream[MAX_PLAYERS];
    private static ServerSocket socket;
    private static boolean dontStop = true;


    public static void main(String[] args){

        try {
            socket = new ServerSocket(PORT);
            System.out.println("Server waiting");
        } catch (Exception e) {}

        try {
            while (true){
                new Handler(socket.accept()).start();
            }
        } catch (Exception e){}

        /**
        try {
            while (true) {
                System.out.println("Server waiting");

                Socket pipe = socket.accept();

                ObjectInputStream objInput = new ObjectInputStream(pipe.getInputStream());
                ObjectOutputStream objOutput = new ObjectOutputStream(pipe.getOutputStream());

                Object o = objInput.readObject();

                System.out.println("Stage 1");

                if (o instanceof String){
                    players[i] = (String) o;
                    writers[i] = objOutput;
                    i++;
                    System.out.println("Player " + (i-1) + players[i-1]);
                }

                System.out.println("Stage 2");

                //test += "+1 test";
                //System.out.println(test);


                System.out.println("Stage 3");

                for (ObjectOutputStream receiver : writers) {
                    receiver.writeObject(o);
                }

                System.out.println("Stage 4");

                objInput.close();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        **/

    }

    public static class Handler extends Thread {
        private Socket socket;
        private ObjectInputStream objIn;
        private ObjectOutputStream objOut;
        private Boolean checkName = false;
        private Object input;

        public Handler (Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                objIn = new ObjectInputStream(socket.getInputStream());
                objOut = new ObjectOutputStream(socket.getOutputStream());

                int zahl = 0;
                while (zahl < 10) {

                    input = objIn.readObject();

                    if (input instanceof String) { // War: while (true)
                        String name = (String) input;

                        if (i > 0) {
                            for (String player : players) {
                                checkName = player.equals(name);
                            }
                        }

                        synchronized (players) {
                            if (!checkName) {
                                players[i] = name;
                                writers[i] = objOut;
                                System.out.println("Player added " + players[i] + writers[i]);
                                i++;
                                // break;
                            }
                        }
                    } else {
                        for (ObjectOutputStream objOutput : writers) {
                            if (objOutput != null){
                                objOutput.writeObject(input);
                            }
                        }
                    }
                    zahl++;
                }

            } catch (Exception e){}
        }

    }

    public void stopServer(){
        dontStop = false;
    }

}