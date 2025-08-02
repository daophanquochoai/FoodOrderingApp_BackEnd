package doctorhoai.learn.authservice.business.foodservice.service.voucherservice;

import doctorhoai.learn.authservice.business.foodservice.model.ExportVoucher;
import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.VoucherDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VoucherFeignFallback implements FallbackFactory<VoucherFeign> {

    private final HandleFallBack fallBack;

    @Override
    public VoucherFeign create(Throwable cause) {
        return new VoucherFeign() {
            @Override
            public ResponseEntity<ResponseObject> getVoucherOfUser(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> addVoucher(VoucherDto voucherDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllVoucher(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getVoucherByCode(String code) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateVoucherById(Integer id, VoucherDto voucherDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> exportVoucehr(ExportVoucher exportVoucher) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
