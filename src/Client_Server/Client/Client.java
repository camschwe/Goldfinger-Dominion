package Client_Server.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    ObjectOutputStream objOutput;
    ObjectInputStream objInput;

    public Client() {

        try {
            Socket socketConnection = new Socket("localhost", 22022);

            System.out.println("versuch");

            objOutput = new ObjectOutputStream(socketConnection.getOutputStream());
            objInput = new ObjectInputStream(socketConnection.getInputStream());

            System.out.println("versuch2");


        } catch (Exception e){}

    }
    public static void main (String[] args){
        Client client = new Client();
        String name = "Benjamin";
        client.sendObject(name);
        int test = 5;
        client.sendObject(test);
        System.out.println("End");
        client.close();
    }

    public void sendObject(Object o){
        try {
            objOutput.writeObject(o);
        } catch (Exception e) {
        }
    }
    public void close(){
        try {
            objInput.close();
            objOutput.close();
        } catch (Exception e) {
        }
    }

}
