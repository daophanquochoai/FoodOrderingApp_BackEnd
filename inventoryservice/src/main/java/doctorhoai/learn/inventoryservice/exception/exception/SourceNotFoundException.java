package doctorhoai.learn.inventoryservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.inventoryservice.exception.contanst.EMessageException;

public class SourceNotFoundException extends NotFound {
    public SourceNotFoundException() {
        super(EMessageException.SOURCE_NOT_FOUND.getMessage());
    }
}
