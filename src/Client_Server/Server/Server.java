package Client_Server.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 22022;

    public static void main(String[] args){

        try {
            ServerSocket socket = new ServerSocket(PORT);
            System.out.println("Server waiting");

            Socket pipe = socket.accept();

            ObjectInputStream objInput = new ObjectInputStream(pipe.getInputStream());
            ObjectOutputStream objOutput = new ObjectOutputStream(pipe.getOutputStream());

            String test = (String)objInput.readObject();
            test += "+1 test";

            objOutput.writeObject(test);

            objInput.close();
            objOutput.close();

        } catch (Exception e) {

        }

    }

}