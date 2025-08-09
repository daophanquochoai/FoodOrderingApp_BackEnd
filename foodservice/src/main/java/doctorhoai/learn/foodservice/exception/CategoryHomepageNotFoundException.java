package doctorhoai.learn.foodservice.exception;

public class CategoryHomepageNotFoundException extends RuntimeException {
    public CategoryHomepageNotFoundException() {
        super(EMessageException.CATEGORY_HOMEPAGE_NOT_FOUND.getMessage());
    }
}
