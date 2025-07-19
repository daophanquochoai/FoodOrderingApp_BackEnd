package doctorhoai.learn.userservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.userservice.exception.contanst.EMessageException;

import java.util.StringJoiner;

public class UserNotFound extends NotFound {
    public UserNotFound(String message, String field) {
        super(String.format(EMessageException.USER_NOT_FOUND.getMessage(), field, message));
    }
}
