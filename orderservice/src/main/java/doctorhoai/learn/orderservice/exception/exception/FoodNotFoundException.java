package doctorhoai.learn.orderservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class FoodNotFoundException extends NotFound {
    public FoodNotFoundException() {
        super(EMessageException.FOOD_NOT_FOUND.getMessage());
    }
}
