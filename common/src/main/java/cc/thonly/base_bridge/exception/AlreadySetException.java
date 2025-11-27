package cc.thonly.base_bridge.exception;

public class AlreadySetException extends RuntimeException {
    public AlreadySetException() {
        super("This value can only be set once!");
    }

    public AlreadySetException(String message) {
        super(message);
    }
}
