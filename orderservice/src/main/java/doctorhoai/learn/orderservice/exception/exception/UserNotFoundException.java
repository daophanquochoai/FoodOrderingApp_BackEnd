package doctorhoai.learn.orderservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class UserNotFoundException extends NotFound {
    public UserNotFoundException() {
        super(EMessageException.USER_NOT_FOUND.getMessage());
    }
}
