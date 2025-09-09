package skrobman.dev.springstart.exception;

public class InvalidProviderException extends NoRequiredParameterException {
    public InvalidProviderException(String message) {
        super(message);
    }
}
