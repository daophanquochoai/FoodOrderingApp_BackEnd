package doctorhoai.learn.orderservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class ShippingFeeConfigNotFoundException extends NotFound {
    public ShippingFeeConfigNotFoundException() {
        super(EMessageException.SHIPPING_FEE_CONFIG_NOT_FOUND.getMessage());
    }
}
