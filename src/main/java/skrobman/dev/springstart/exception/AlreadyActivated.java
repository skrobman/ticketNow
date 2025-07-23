package skrobman.dev.springstart.exception;

public class AlreadyActivated extends RuntimeException {
    public AlreadyActivated(String message) {
        super(message);
    }
}
