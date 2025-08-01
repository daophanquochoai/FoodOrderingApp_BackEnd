package doctorhoai.learn.orderservice.feign.foodservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.feign.function.HandleFallBack;
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
            public ResponseEntity<ResponseObject> getVoucherById(Integer id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getVoucherByMul(List<Integer> ids) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
