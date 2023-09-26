package bluetix.exception;

public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(String message) {
        super("DataIntegrityViolationException:" + message + ".");
    }
}

//This exception is thrown when there's a violation of data integrity constraints, such as a unique constraint violation.