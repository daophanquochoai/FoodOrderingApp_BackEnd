package doctorhoai.learn.foodservice.controller;

public enum EMessageResponse {

    GET_FOOD("Get food successful"),
    CREATE_FOOD("Create food successful"),
    UPDATE_FOOD("Update food successful"),
    FOOD_FOUND("Food found successful"),

    GET_CATEGORY("Get category successful"),
    CREATE_CATEGORY("Create category successful"),
    UPDATE_CATEGORY("Update category successful"),

    GET_VOUCHER("Get voucher successful"),
    CREATE_VOUCHER("Create voucher successful"),
    UPDATE_VOUCHER("Update voucher successful"),

    CREATE_SIZE_SUCCESSFUL("Create size successful"),
    UPDATE_SIZE_SUCCESSFUL("Update size successful"),
    GET_SIZE_SUCCESSFUL("Get size successful"),

    CREATE_FOOD_SIZE_SUCCESSFUL("Create food size successful"),
    UPDATE_FOOD_SIZE_SUCCESSFUL("Update food size successful"),
    GET_FOOD_SIZE_SUCCESSFUL("Get food size successful"),

    GET_FILTER("Get filter successful"),
    ;
    private String message;
    EMessageResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
