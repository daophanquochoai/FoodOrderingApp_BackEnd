package doctorhoai.learn.inventoryservice.controller;

public enum EMessageResponse {
    GET_SOURCE("Get source successful"),
    CREATE_SOURCE("Create source successful"),
    UPDATE_SOURCE("Update source successful"),

    CREATE_HISTORY_IMPORT_OR_EXPORT("Create history import or export successful"),
    GET_HISTORY_IMPORT_OR_EXPORT("Get history import or export"),
    UPDATE_HISTORY_IMPORT_OR_EXPORT("Update history import or export"),

    CREATE_INGREDIENTS_SUCCESSFUL("Create ingredients successful"),
    GET_INGREDIENTS_SUCCESSFUL("Get ingredients successful"),
    UPDATE_INGREDIENTS_SUCCESSFUL("Update ingredients successful"),

    CREATE_INGREDIENTS_ERROR_SUCCESSFUL("Create ingredients error successful"),
    GET_INGREDIENTS_ERROR_SUCCESSFUL("Get ingredients error successful"),
    UPDATE_INGREDIENTS_ERROR_SUCCESSFUL("Update ingredients error successful"),

    CREATE_FOOD_INGREDIENTS_SUCCESSFUL("Create food ingredients successful"),
    GET_FOOD_INGREDIENTS_SUCCESSFUL("Get food ingredients successful"),
    UPDATE_FOOD_INGREDIENTS_SUCCESSFUL("Update food ingredients successful"),

    CHECK_ORDER_SUCCESSFUL("Check order successful"),
    GET_FILTER_SUCCESFUL("Get filter successful"),
    ;
    private String message;
    EMessageResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
