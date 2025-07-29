package doctorhoai.learn.authservice.business.orderservice.service.pointservice;

import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "orderservice",
        path = "/point",
        contextId = "pointBusiness",
        fallbackFactory = PointFeignFallback.class,
        configuration = FeignConfig.class
)
public interface PointFeign {
    @GetMapping("/{userId}")
    ResponseEntity<ResponseObject> getPointOfUser(@PathVariable("userId") int userId);
}
