package doctorhoai.learn.foodservice.exception;

public enum EMessageException {

    VOUCHER_NOT_FOUND("Voucher Not Found"),
    CATEGORY_NOT_FOUND("Category not found"),
    FOOD_NOT_FOUND("Food not found"),
    USER_NOT_FOUND("User not found"),
    SIZE_NOT_FOUND("Size not found"),
    FOOD_SIZE_NOT_FOUND("Food Size not found"),

    NAME_SIZE_DUPLICATE("Name size duplicate"),
    VOUCHER_DUPLICATE("Code duplicate"),
    ;

    private String message;
    EMessageException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
