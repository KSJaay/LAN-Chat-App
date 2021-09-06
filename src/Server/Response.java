package Server;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author KSJaay
 */
public class Response {

    public enum PATH {
        SERVER,
        CLIENT,
        GROUP
    }

    public static void emit(PATH path, String message) {
        //Emit Message to whole group or to the Server
        switch (path) {
            case SERVER:
                Server.getInstance().addNewMessage(message);
                break;
            case GROUP:
                Server.getInstance().client_addNewMessage(message);
                break;
            default:
                break;
        }
    }

    public static void emit(PATH path, String message, DataOutputStream dos) throws IOException {
        //Emit message to the sender 
        if (path == PATH.CLIENT) {
            dos.writeUTF(message);
        }
    }
}
