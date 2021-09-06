package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author KSJaay
 */
public final class Server {

    private Boolean serverStarted = false;
    private HashMap<Integer, Handler> Clients;
    private String Messages;
    private String ClientMessages = "COMP1549 \n=================";
    private Socket socket;
    private ServerSocket server_socket;
    private Integer port;

    private Server() {
        Clients = new HashMap<>();
        port = 3310; //Default port
        Messages = "";
        this.addNewMessage("Server was launched");
    }

    private static class server_instance {

        private static final Server INSTANCE = new Server();
    }

    public static Server getInstance() {
        return server_instance.INSTANCE;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void ConfigServer(Integer Port) {
        port = Port;
    }

    public String getMessages() {
        return this.Messages;
    }

    public void addNewMessage(String str) {
        Messages += "\n" + str;
    }

    public String client_getMessages() {
        return this.ClientMessages;
    }

    public void client_addNewMessage(String str) {
        ClientMessages += "\n" + str;
    }

    public HashMap<Integer, Handler> getClients() {
        return Clients;
    }

    public Boolean isFirstMember() {
        return Clients.isEmpty();
    }

    public void ChangeCoordinator() {
        //Random to the size of clients then set as coordinator
        System.out.println("Changing Coordinator");
        for (Map.Entry<Integer, Handler> entry : Clients.entrySet()) {

            entry.getValue().isCoordinator = true;
            String cm = entry.getValue().UNAME + " is the new coordinator"; //Constructed Message
            Response.emit(Response.PATH.GROUP, cm);
            System.out.println(cm);
            break;
        }
    }

    public void addNewClient(Integer UserID, Handler client) {
        Clients.put(UserID, client);
    }

    public Boolean startServer() {
        try {
            server_socket = new ServerSocket(port);
            this.addNewMessage("Started Connection to port: " + port);
            serverStarted = true;
            return true;
        } catch (Exception e) {
            this.addNewMessage("Error catched:  " + e.getMessage());
        }
        return false;
    }

    public Thread runServer() {
        return new Thread(() -> {
            while (true) {

                try {
                    //if new client enters the server

                    socket = server_socket.accept();
                    this.addNewMessage("\nA Client attempting to connect to the server: " + socket.getInetAddress());

                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                    Handler client = new Handler(socket, dis, dos);

                    //adding to thread pool
                    Thread th = new Thread(client);
                    th.start();

                } catch (IOException ex) {
                    this.addNewMessage("Error catched:  " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    System.err.println("test");
                }
            }
        });
    }
}
