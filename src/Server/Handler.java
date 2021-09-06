package Server;

import Server.Response.PATH;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author KSJaay
 */
public class Handler implements Runnable {

    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isUserLoggedIn;
    String UNAME;
    Integer UID;
    Boolean isCoordinator;
    boolean _uname_passed;

    String current_members = "CURRENT ACTIVE MEMBERS:\n\n";

    public Handler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
        this.isUserLoggedIn = false;
        this._uname_passed = true;
        this.isCoordinator = false;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {

                received = dis.readUTF();

                String command = received.split("#")[0];
                String message = received.split("#")[1];

                StringTokenizer st = new StringTokenizer(message, ",");
                try {
                    UID = Integer.parseInt(st.nextToken());
                } catch (NumberFormatException ex) {
                    this.isUserLoggedIn = false;
                    String cm = "\nUnable to enter this server, UID Is invalid, please check your credentials"; //constructed message
                    this.dos.writeUTF("false" + "#" + cm);
                    //this.s.close();
                    break; //breaking loop
                }

                UNAME = st.nextToken();
                String u_message = st.nextToken();

                if (command.equals("logout")) {
                    Response.emit(PATH.SERVER, "\nDisconneting " + this.UID + " [ " + this.UNAME + " ]");
                    if (Server.getInstance().getClients().containsKey(UID)) {
                        this.isUserLoggedIn = false;
                        Server.getInstance().getClients().remove(UID); //removing to client list
                        String sm = "\nClient  " + this.UID + " [ " + this.UNAME + " ] was properly disconnected";
                        Response.emit(PATH.SERVER, sm);
                        Response.emit(PATH.GROUP, sm);

                        if (this.isCoordinator) {
                            Response.emit(PATH.GROUP, "COORDINATOR " + this.UNAME + " was disconnected");
                            Server.getInstance().ChangeCoordinator();
                        }

                        break; //breaking loop
                    }
                }

                if (command.equals("login")) {
                    Server.getInstance().addNewMessage("\nLogging in " + this.UID + " [ " + this.UNAME + " ]");

                    this.isUserLoggedIn = true;
                    if (Server.getInstance().getClients().containsKey(UID)) {
                        this.isUserLoggedIn = false;
                        String cm = "\nUnable to enter this server, UID is already connected to the server. Please check your credentials"; //constructed message
                        this.dos.writeUTF("false" + "#" + cm);
                        break; //breaking loop
                    }

                    Server.getInstance().getClients().forEach((i, h) -> {
                        if (h.UNAME.equals(this.UNAME)) {
                            this._uname_passed = false;
                        }
                    });

                    if (!_uname_passed) {
                        this.isUserLoggedIn = false;
                        String cm = "\nUnable to enter this server, Username is already connected to the server. Please check your credentials"; //constructed message
                        this.dos.writeUTF("false" + "#" + cm);
                        break; //breaking loop
                    }

                    if (this.isUserLoggedIn) {
                        this.current_members = "CURRENT ACTIVE MEMBERS:\n\n";
                        this.isCoordinator = Server.getInstance().isFirstMember();
                        Server.getInstance().addNewClient(UID, this);
                        Response.emit(PATH.CLIENT, "true#You were successfully connected to the server.\n\n" + (this.isCoordinator ? "You are the current Group Coordinator" : ""), dos);
                        Response.emit(PATH.SERVER, "Client " + this.UNAME + " was added to active members");

                        HashMap<Integer, Handler> clients = Server.getInstance().getClients();
                        clients.forEach((i, f) -> {
                            this.current_members += "• " + (f.isCoordinator ? "<COORDINATOR>_" : "") + f.UNAME + "\n";
                        });

                        String cm = "\nWelcome! " + this.UNAME + ", ID: " + this.UID + "\n\n" + this.current_members + "\n"; //constructed message
                        Response.emit(PATH.GROUP, cm);
                    }

                }

                if (command.equals("refreshMessages")) {
                    HashMap<Integer, Handler> clients = Server.getInstance().getClients();
                    if (clients.containsKey(UID)) {
                        Handler client = clients.get(UID);
                        try {
                            Response.emit(PATH.CLIENT, Server.getInstance().client_getMessages(), client.dos);
                        } catch (IOException ex) {
                            Response.emit(PATH.SERVER, "\n" + ex.getMessage());
                        }
                    } else {
                        this.isUserLoggedIn = false;
                        this.s.close();
                        break; //breaking loop
                    }
                }

                if (command.equals("getClients")) {
                    HashMap<Integer, Handler> clients = Server.getInstance().getClients();
                    if (clients.containsKey(UID)) {
                        Handler client = clients.get(UID);
                        try {
                            List<String> ls = new ArrayList<>();
                            clients.forEach((i, f) -> {
                                ls.add((f.isCoordinator ? "<COORDINATOR>_" : "") + f.UNAME);
                            });
                            Response.emit(PATH.CLIENT, ls.toString(), client.dos);
                        } catch (IOException ex) {
                            Response.emit(PATH.SERVER, "\n" + ex.getMessage());
                        }
                    } else {
                        this.isUserLoggedIn = false;
                        this.s.close();
                        break; //breaking loop
                    }
                }

                if (command.equals("send")) {
                    HashMap<Integer, Handler> clients = Server.getInstance().getClients();
                    if (clients.containsKey(UID)) {
                        Handler client = clients.get(UID);
                        if (client.isUserLoggedIn) {
                            String ClientMessage = "\n"
                                    + (client.isCoordinator ? "[COORDINATOR] " : "") + "< " + client.UNAME + " > :: \t" + this.applyTimeStamp() + "\n"
                                    + "-------------------------------------------------------------------------------------------\n"
                                    + "MESSAGE: " + u_message + "\n";

                            Response.emit(PATH.GROUP, ClientMessage);
                            Response.emit(PATH.SERVER, ClientMessage);
                        }
                    } else {
                        this.isUserLoggedIn = false;
                        this.s.close();
                        break; //breaking loop
                    }
                }
            } catch (SocketException e) {
                if (e.toString().contains("Socket closed") || e.toString().contains("Connection reset")
                        || e.toString().contains("Broken pipe")) {
                    Server.getInstance().getClients().remove(UID); //removing to client list

                    if (this.isCoordinator) {
                        Response.emit(PATH.GROUP, "COORDINATOR " + this.UNAME + " was disconnected");
                        Server.getInstance().ChangeCoordinator();
                    }

                    break;
                }
            } catch (IOException e) {
                Response.emit(PATH.SERVER, "\n" + e.getMessage());
            }
        }

        try {
            //Cleaning
            this.dis.close();
            this.dos.close();
        } catch (IOException e) {
            Response.emit(PATH.SERVER, "\n" + e.getMessage());
        }
    }

    private String applyTimeStamp() {
        Calendar cal = Calendar.getInstance();
 
        SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss"); //HH:mm:ss Or EEE, d MMM yyyy HH:mm:ss
        return "(" + timeOnly.format(cal.getTime()) + ")";
    }
}
