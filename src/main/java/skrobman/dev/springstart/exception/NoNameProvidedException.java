package skrobman.dev.springstart.exception;

public class NoNameProvidedException extends NoRequiredParameterException {
    public NoNameProvidedException(String message) {
        super(message);
    }
}
