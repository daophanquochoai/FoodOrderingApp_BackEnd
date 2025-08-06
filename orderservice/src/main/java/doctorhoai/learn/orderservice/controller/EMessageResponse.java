package doctorhoai.learn.orderservice.controller;

public enum EMessageResponse {
    CREATE_PAYMENT_SUCCESSFUL("Create payment successful"),
    UPDATE_PAYMENT_SUCCESSFUL("Update payment successful"),
    GET_PAYMENT_SUCCESSFUL("Get payment successful"),

    CREATE_CART_SUCCESSFUL("Create cart successful"),
    UPDATE_CART_SUCCESSFUL("Update cart successful"),
    GET_CART_SUCCESSFUL("Get cart successful"),

    GET_SHIPPING_FEE_CONFIG_SUCCESSFUL("Get shipping fee config successful"),
    CREATE_SHIPPING_FEE_SUCCESSFUL("Create shipping fee successful"),

    ORDER_SUCCESSFUL("Order successful"),
    GET_ORDER_SUCCESSFUL("Get order successful"),

    GET_POINT_SUCCESSFUL("Get point successful"),
    CREATE_POINT_SUCCESSFUL("Create point successful"),

    CHANGE_ORDER_SUCCESSFUL("Change order successful"),
    ;

    private String message;

    EMessageResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
