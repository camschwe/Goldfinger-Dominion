package Client_Server.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final int PORT = 22022;

    public static void main(String[] args){

        try {
            String tester = "Test Client ";
            System.out.println(tester);

            Socket serverSocket = new Socket("127.0.0.1", PORT);

            ObjectOutputStream objOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream objInput = new ObjectInputStream(serverSocket.getInputStream());

            objOutput.writeObject(tester);

            tester = (String)objInput.readObject();

            System.out.println(tester);

            objInput.close();
            objOutput.close();

        } catch (Exception e) {}
    }
}
