package nye.progtech.exceptions;

public class CouldNotReachDatabase extends RuntimeException {
    public CouldNotReachDatabase(String message, Throwable cause) {
        super(message, cause);
    }
}
