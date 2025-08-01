package doctorhoai.learn.userservice.exception.exception;

import doctorhoai.learn.basedomain.exception.BadException;
import doctorhoai.learn.userservice.exception.contanst.EMessageException;

public class PasswordNotCorrect extends BadException {
    public PasswordNotCorrect() {
        super(EMessageException.PASSWORD_NOT_CORRECT.getMessage());
    }
}
