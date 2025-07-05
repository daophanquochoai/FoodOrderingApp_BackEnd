package doctorhoai.learn.userservice.exception.exception;

import doctorhoai.learn.userservice.exception.contanst.EMessageException;

public class RoleNotFound extends NotFound{
    public RoleNotFound() {
        super(EMessageException.ROLE_NOT_FOUND.getMessage());
    }
}
