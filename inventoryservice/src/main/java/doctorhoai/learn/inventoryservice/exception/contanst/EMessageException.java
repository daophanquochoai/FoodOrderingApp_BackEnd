package doctorhoai.learn.inventoryservice.exception.contanst;

public enum EMessageException {
    SOURCE_NOT_FOUND("Source not found"),
    INGREDIENTS_NOT_FOUND("Ingredients not found"),
    INGREDIENTS_ERROR_NOT_FOUND("Ingredients error not found"),
    SERVICE_DOWN("Service down"),
    FOOD_NOT_FOUND("Food not found"),
    ORDER_ITEM_NOT_FOUND("Order item not found"),
    ;
    private String message;
    EMessageException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
