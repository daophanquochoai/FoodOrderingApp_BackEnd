package doctorhoai.learn.userservice.exception.exception;

import doctorhoai.learn.basedomain.exception.Duplicate;
import doctorhoai.learn.userservice.exception.contanst.EMessageException;

public class CCCDDuplicate extends Duplicate {
    public CCCDDuplicate() {
        super(EMessageException.CCCD_DUPLICATE.getMessage());
    }
}
