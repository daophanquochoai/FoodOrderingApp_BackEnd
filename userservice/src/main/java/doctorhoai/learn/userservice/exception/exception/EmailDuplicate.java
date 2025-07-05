package doctorhoai.learn.userservice.exception.exception;

import doctorhoai.learn.userservice.exception.contanst.EMessageException;

public class EmailDuplicate extends Duplicate {
    public EmailDuplicate() {
        super(EMessageException.EMAIL_DUPLICATE.getMessage());
    }
}
