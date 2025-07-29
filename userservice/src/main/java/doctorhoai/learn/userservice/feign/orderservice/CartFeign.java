package doctorhoai.learn.userservice.feign.orderservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.userservice.config.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "orderservice",
        path = "/cart",
        contextId = "cartUser",
        fallbackFactory = CartFeignFallback.class,
        configuration = FeignConfig.class
)
public interface CartFeign {
    @PostMapping("/cart/{userId}")
    public ResponseEntity<ResponseObject> createCart(
            @PathVariable Integer userId
    );
}
