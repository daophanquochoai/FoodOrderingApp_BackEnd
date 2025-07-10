package doctorhoai.learn.foodservice.service.voucher;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.VoucherDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.model.enums.EStatusVoucher;

import java.util.List;

public interface VoucherService {
    List<VoucherDto> getVoucherOfUser(Filter filter);

    PageObject getAllVouchers(Filter filter);

    VoucherDto createVoucher(VoucherDto voucherDto);

    VoucherDto getVoucherByCode(String code);
}
