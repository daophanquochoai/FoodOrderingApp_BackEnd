package doctorhoai.learn.inventoryservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.inventoryservice.exception.contanst.EMessageException;

public class OrderItemNotFoundException extends NotFound {
    public OrderItemNotFoundException() {
        super(EMessageException.ORDER_ITEM_NOT_FOUND.getMessage());
    }
}
