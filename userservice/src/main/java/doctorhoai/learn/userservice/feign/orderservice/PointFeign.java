package doctorhoai.learn.userservice.feign.orderservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.userservice.config.feign.FeignConfig;
import doctorhoai.learn.userservice.feign.model.PointDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "orderservice",
        path = "/point",
        contextId = "pointBusiness",
        fallbackFactory = PointFeignFallback.class,
        configuration = FeignConfig.class
)
public interface PointFeign {
    @PostMapping("/create")
    ResponseEntity<ResponseObject> createPoint(@RequestBody PointDto point) ;
}
