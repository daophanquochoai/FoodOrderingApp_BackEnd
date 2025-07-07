package doctorhoai.learn.foodservice.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class UserNotFoundException extends NotFound {
    public UserNotFoundException() {
        super(EMessageException.USER_NOT_FOUND.getMessage());
    }
}
