package doctorhoai.learn.authservice.business.orderservice.service.orderservice;

import doctorhoai.learn.authservice.business.orderservice.model.Filter;
import doctorhoai.learn.authservice.business.orderservice.model.OrderDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "orderservice",
        path = "/order",
        contextId = "orderBusiness",
        fallbackFactory = OrderFeignFallback.class,
        configuration = FeignConfig.class
)
public interface OrderFeign {
    @PostMapping("/order")
    ResponseEntity<ResponseObject> order(
            @RequestBody OrderDto orderDto
    );
    @PostMapping("/all")
    ResponseEntity<ResponseObject> all(
            @RequestBody Filter filter
    );
}
