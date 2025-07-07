package doctorhoai.learn.foodservice.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class FoodNotFoundException extends NotFound {
    public FoodNotFoundException() {
        super(EMessageException.FOOD_NOT_FOUND.getMessage());
    }
}
