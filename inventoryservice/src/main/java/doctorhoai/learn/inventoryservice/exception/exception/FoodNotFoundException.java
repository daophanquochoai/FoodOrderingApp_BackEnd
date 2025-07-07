package doctorhoai.learn.inventoryservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.inventoryservice.exception.contanst.EMessageException;

public class FoodNotFoundException extends NotFound {
    public FoodNotFoundException() {
        super(EMessageException.FOOD_NOT_FOUND.getMessage());
    }
}
