package doctorhoai.learn.inventoryservice.exception.exception;

import doctorhoai.learn.basedomain.exception.BadException;
import doctorhoai.learn.inventoryservice.exception.contanst.EMessageException;

public class NotEnoughIngredientException extends BadException {
    public NotEnoughIngredientException() {
        super(EMessageException.NOT_ENOUGH_INGREDIENTS.getMessage());
    }
}
