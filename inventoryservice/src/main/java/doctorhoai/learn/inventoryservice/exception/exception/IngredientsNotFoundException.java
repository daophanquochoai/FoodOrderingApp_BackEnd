package doctorhoai.learn.inventoryservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.inventoryservice.exception.contanst.EMessageException;

public class IngredientsNotFoundException extends NotFound {
    public IngredientsNotFoundException() {
        super(EMessageException.INGREDIENTS_NOT_FOUND.getMessage());
    }
}
