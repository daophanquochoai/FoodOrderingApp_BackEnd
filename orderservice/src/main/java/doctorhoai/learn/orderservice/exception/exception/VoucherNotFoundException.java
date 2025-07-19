package doctorhoai.learn.orderservice.exception.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class VoucherNotFoundException extends NotFound {
    public VoucherNotFoundException() {
        super(EMessageException.VOUCHER_NOT_FOUND.getMessage());
    }
}
