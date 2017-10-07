package Client_Server.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    public Client() {

        try {
            Socket socketConnection = new Socket("localhost", 22022);

            ObjectOutputStream objOutput = new ObjectOutputStream(socketConnection.getOutputStream());
            ObjectInputStream objInput = new ObjectInputStream(socketConnection.getInputStream());

            // objOutput.writeObject(TODO);

            // TODO = objInput.readObject();
            //Testen

            objInput.close();
            objOutput.close();

        } catch (Exception e){}

    }

}
