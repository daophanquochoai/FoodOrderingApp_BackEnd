package doctorhoai.learn.foodservice.exception;

public class FoodHomepageNotFoundException extends RuntimeException {
    public FoodHomepageNotFoundException() {
        super(EMessageException.FOOD_HOMEPAGE_NOT_FOUND.getMessage());
    }
}
