package nye.progtech.exceptions;

public class CouldNotSaveGame extends RuntimeException {
    public CouldNotSaveGame(String message, Throwable cause) {
        super(message, cause);
    }
}