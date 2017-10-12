package Client_Server.Client;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {
    private static final int PORT = 22022;

    public static void main(String[] args){


        try {
            String tester = "Test Client";
            System.out.println(tester);

            Socket serverSocket = new Socket("127.0.0.1", PORT);

            ObjectOutputStream objOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream objInput = new ObjectInputStream(serverSocket.getInputStream());

            objOutput.writeObject(tester);

            int zahl = 0;
            Integer test2;
            while (zahl < 5){
                test2 = zahl;
                objOutput.writeObject(test2);

                test2 = (Integer) objInput.readObject();

                System.out.println(test2);
                zahl++;

            }

            //objInput.close();
            //objOutput.close();

            while (true){
                Object o = objInput.readObject();
                doSomething(o);
            }

        } catch (Exception e) {}
    }

    public void run(){

    }

    public void start(){
        new Thread(this).start();
    }

    public static void doSomething(Object o){

    }
}
