package doctorhoai.learn.foodservice.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class FoodSizeException extends NotFound {
    public FoodSizeException() {
        super(EMessageException.FOOD_SIZE_NOT_FOUND.getMessage());
    }
}
