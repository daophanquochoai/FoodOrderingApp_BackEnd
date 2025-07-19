package doctorhoai.learn.orderservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class OrderNotFoundException extends NotFound {
    public OrderNotFoundException() {
        super(EMessageException.ORDER_NOT_FOUND.getMessage());
    }
}
