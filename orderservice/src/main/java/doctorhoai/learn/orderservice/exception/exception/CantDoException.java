package doctorhoai.learn.orderservice.exception.exception;

public class CantDoException extends RuntimeException {
    public CantDoException(String message) {
        super(message);
    }
}
