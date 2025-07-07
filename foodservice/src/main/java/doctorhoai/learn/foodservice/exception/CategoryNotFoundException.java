package doctorhoai.learn.foodservice.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class CategoryNotFoundException extends NotFound {
    public CategoryNotFoundException() {
        super(EMessageException.CATEGORY_NOT_FOUND.getMessage());
    }
}
