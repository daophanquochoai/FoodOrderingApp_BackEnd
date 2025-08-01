package doctorhoai.learn.foodservice.service.voucher;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.VoucherDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;

import java.util.List;

public interface VoucherService {
    List<VoucherDto> getVoucherOfUser(Filter filter);

    PageObject getAllVouchers(Filter filter);

    VoucherDto createVoucher(VoucherDto voucherDto);

    VoucherDto getVoucherByCode(String code);

    VoucherDto getVoucherById(Integer id);

    String updateVoucher( Integer id);
    String updateRollbackVoucher( Integer id);

    List<VoucherDto> getVoucherByIds(List<Integer> ids);
}
