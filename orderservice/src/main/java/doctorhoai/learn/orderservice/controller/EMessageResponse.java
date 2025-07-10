package doctorhoai.learn.orderservice.controller;

public enum EMessageResponse {
    CREATE_PAYMENT_SUCCESSFUL("Create payment successful"),
    UPDATE_PAYMENT_SUCCESSFUL("Update payment successful"),
    GET_PAYMENT_SUCCESSFUL("Get payment successful"),

    CREATE_CART_SUCCESSFUL("Create cart successful"),
    UPDATE_CART_SUCCESSFUL("Update cart successful"),
    GET_CART_SUCCESSFUL("Get cart successful"),
    ;

    private String message;

    EMessageResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
