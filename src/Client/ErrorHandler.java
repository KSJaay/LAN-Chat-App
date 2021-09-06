package Client;

/**
 *
 * @author KSJaay
 */
public class ErrorHandler {

    private ErrorHandler() {

    }

    private static class instance {

        private static final ErrorHandler INSTANCE = new ErrorHandler();
    }

    public static ErrorHandler getInstance() {
        return instance.INSTANCE;
    }

    public void Handle(Exception e, Callback<String, Boolean> callback) {
        String error = e.getMessage();
        Boolean required_to_reset_sock = false;

        if (e.getMessage().equals("Connection reset")) {
            error = "Server unexpectedly disconnected or crashed.\n" + e.getMessage();
            required_to_reset_sock = true;
        }

        if (e.getMessage().equals("Software caused connection abort: recv failed")) {
            error = "You were disconnected to the server. Please check your User ID and Username .\n" + e.getMessage();
            required_to_reset_sock = true;
        }

        if (e.getMessage().equals("Connection reset by peer: socket write error")) {
            error = "Server unexpectedly disconnected or crashed.\n" + e.getMessage();
            required_to_reset_sock = true;
        }

        if (e.getMessage().equals("Connection refused: connect")) {
            error = "Can't connect to the server \n" + e.getMessage();
        }

        if (e.getMessage().equals("Software caused connection abort: socket write error")) {
            error = "Unable to send to server\n" + e.getMessage();
        }

        callback.result_set(error, required_to_reset_sock);

    }
}
