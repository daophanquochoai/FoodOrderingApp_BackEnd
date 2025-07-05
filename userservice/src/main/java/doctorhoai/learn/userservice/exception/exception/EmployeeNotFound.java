package doctorhoai.learn.userservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.userservice.exception.contanst.EMessageException;

public class EmployeeNotFound extends NotFound {
    public EmployeeNotFound() {
        super(EMessageException.EMPLOYEE_NOT_FOUND.getMessage());
    }
}
