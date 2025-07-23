package skrobman.dev.springstart.exception;

public class EmailDoesNotExist extends RuntimeException {
    public EmailDoesNotExist(String message) {
        super(message);
    }
}
