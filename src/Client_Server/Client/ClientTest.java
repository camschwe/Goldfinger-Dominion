package Client_Server.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientTest {
    ObjectOutputStream objOutput;
    ObjectInputStream objInput;
    private final int PORT = 22022;

    public ClientTest() {

    }

    private void run() throws IOException {
        Socket socket = new Socket("localhost", PORT);
        System.out.println("Connected to Server");
        objInput = new ObjectInputStream(socket.getInputStream());
        objOutput = new ObjectOutputStream(socket.getOutputStream());

        while (true){
            try {
                Object o = objInput.readObject();
            } catch (ClassNotFoundException e) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ClientTest client = new ClientTest();
        client.run();
    }
}
