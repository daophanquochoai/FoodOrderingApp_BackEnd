package doctorhoai.learn.userservice.exception.exception;

import doctorhoai.learn.userservice.exception.contanst.EMessageException;

public class PhoneNumberDuplicate extends Duplicate{

    public PhoneNumberDuplicate() {
        super(EMessageException.PHONE_NUMBER_DUPLICATE.getMessage());
    }
}
