package doctorhoai.learn.inventoryservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.inventoryservice.exception.contanst.EMessageException;

public class IngredientsErrorNotFoundException extends NotFound {
    public IngredientsErrorNotFoundException() {
        super(EMessageException.INGREDIENTS_ERROR_NOT_FOUND.getMessage());
    }
}
