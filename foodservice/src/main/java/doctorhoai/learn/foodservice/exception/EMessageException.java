package doctorhoai.learn.foodservice.exception;

public enum EMessageException {

    VOUCHER_NOT_FOUND("Voucher Not Found"),
    CATEGORY_NOT_FOUND("Category not found"),
    FOOD_NOT_FOUND("Food not found"),
    USER_NOT_FOUND("User not found"),
    ;

    private String message;
    EMessageException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
