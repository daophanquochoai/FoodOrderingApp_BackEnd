package doctorhoai.learn.foodservice.exception;

import doctorhoai.learn.basedomain.exception.Duplicate;

public class VoucherDuplicate extends Duplicate {
    public VoucherDuplicate() {
        super(EMessageException.VOUCHER_DUPLICATE.getMessage());
    }
}
