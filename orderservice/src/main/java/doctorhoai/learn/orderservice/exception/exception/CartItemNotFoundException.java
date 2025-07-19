package doctorhoai.learn.orderservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class CartItemNotFoundException extends NotFound {
    public CartItemNotFoundException() {
        super(EMessageException.CART_ITEM_NOT_FOUND.getMessage());
    }
}
