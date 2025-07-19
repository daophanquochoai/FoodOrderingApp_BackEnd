package doctorhoai.learn.aiservice.feign.foodservice;

import doctorhoai.learn.aiservice.feign.config.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "foodservice", contextId = "aiFood", path = "/food", fallbackFactory = FoodFeignFallback.class, configuration = FeignConfig.class)
public interface FoodFeign {
    @GetMapping("/food/all")
    ResponseEntity<ResponseObject> getAllFoodNoFilter();
}
