package Client_Server.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server(){

        while (true) {
            try {
                ServerSocket socket = new ServerSocket(22022);
                System.out.println("Server waiting");

                Socket server = socket.accept();

                ObjectInputStream objInput = new ObjectInputStream(server.getInputStream());

                ObjectOutputStream objOutput = new ObjectOutputStream(server.getOutputStream());

                Object obj = objInput.readObject();

                objOutput.writeObject(obj);

                objInput.close();
                objOutput.close();
            } catch (Exception e) {

            }
        }
    }


}
