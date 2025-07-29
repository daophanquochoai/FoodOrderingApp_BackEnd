package doctorhoai.learn.authservice.business.orderservice.service.shipservice;

import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipFeignFallback implements FallbackFactory<ShipFeign> {

    private final HandleFallBack fallBack;

    @Override
    public ShipFeign create(Throwable cause) {
        return new ShipFeign() {
            @Override
            public ResponseEntity<ResponseObject> getShippingFeeConfig() {
                return fallBack.processFallback(cause);
            }
        };
    }
}
