package doctorhoai.learn.foodservice.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class SizeNotFoundException extends NotFound {
    public SizeNotFoundException() {
        super(EMessageException.SIZE_NOT_FOUND.getMessage());
    }
}
