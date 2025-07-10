package doctorhoai.learn.orderservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class CartNotFoundException extends NotFound {
    public CartNotFoundException() {
        super(EMessageException.CART_NOT_FOUND.getMessage());
    }
}
