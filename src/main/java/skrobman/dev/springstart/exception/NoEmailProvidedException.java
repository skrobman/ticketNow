package skrobman.dev.springstart.exception;

public class NoEmailProvidedException extends NoRequiredParameterException {
    public NoEmailProvidedException(String message) {
        super(message);
    }
}
