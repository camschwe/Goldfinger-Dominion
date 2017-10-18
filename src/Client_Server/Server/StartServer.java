package Client_Server.Server;

public class StartServer extends Thread {
    private ServerTest testServer;

    public StartServer(){
    }

    public void run(){
        try {
            testServer = new ServerTest();
        } catch (Exception e) {
        }
    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }
}
