package doctorhoai.learn.foodservice.exception;

import doctorhoai.learn.basedomain.exception.NotFound;

public class VoucherNotFoundException extends NotFound {
    public VoucherNotFoundException() {
        super(EMessageException.VOUCHER_NOT_FOUND.getMessage());
    }
}
