package doctorhoai.learn.orderservice.exception.exception;

public enum EMessageException {

    PAYMENT_NOT_FOUND("Payment Not Found"),
    FOOD_NOT_FOUND("Food Not Found"),
    CART_ITEM_NOT_FOUND("Cart Item Not Found"),
    CART_NOT_FOUND("Cart Not Found"),
    ;


    private String message;
    EMessageException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
