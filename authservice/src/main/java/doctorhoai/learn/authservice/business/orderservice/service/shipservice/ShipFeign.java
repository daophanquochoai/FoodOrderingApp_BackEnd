package doctorhoai.learn.authservice.business.orderservice.service.shipservice;

import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "orderservice",
        path = "/shipping_fee_config",
        contextId = "shipBusiness",
        fallbackFactory = ShipFeignFallback.class,
        configuration = FeignConfig.class
)
public interface ShipFeign {
    @GetMapping
    ResponseEntity<ResponseObject> getShippingFeeConfig();
}
