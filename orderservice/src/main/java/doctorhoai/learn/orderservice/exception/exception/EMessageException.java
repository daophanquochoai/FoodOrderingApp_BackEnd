package doctorhoai.learn.orderservice.exception.exception;

public enum EMessageException {

    PAYMENT_NOT_FOUND("Payment Not Found"),
    FOOD_NOT_FOUND("Food Not Found"),
    CART_ITEM_NOT_FOUND("Cart Item Not Found"),
    CART_NOT_FOUND("Cart Not Found"),
    USER_NOT_FOUND("User Not Found"),
    VOUCHER_NOT_FOUND("Voucher Not Found"),
    SHIPPING_FEE_CONFIG_NOT_FOUND("Shipping Fee Config Not Found"),

    USER_NOT_HAVE_VOUCHER("User Not Have Voucher"),
    VOUCHER_CANT_USE("Voucher Cant Use"),

    TOTAL_COST_NOT_CORRECT("Total Cost Not Correct"),
    ORDER_NOT_FOUND("Order Not Found");
    ;


    private String message;
    EMessageException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
