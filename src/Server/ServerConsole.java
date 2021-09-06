package Server;

import java.util.logging.Level;
import java.util.logging.Logger;
import Server.Server;

/**
 *
 * @author KSJaay
 */
public class ServerConsole {

    Server server = Server.getInstance();
    Thread getMessageThread;
    Thread getClients;
    String serverMessages = "";

    public static void main(String[] args) {
        new ServerConsole().init();
    }

    public void init() {

        if (server.startServer()) {
            server.runServer().start();
        }

        getMessageThread = new Thread(() -> {
            while (true) {
                try { //puting some delay 
                    Thread.sleep(10);
                    if (serverMessages.length() != server.getMessages().length()) {
                        serverMessages = server.getMessages();
                        System.out.println(serverMessages);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        getMessageThread.start();
    }
}
