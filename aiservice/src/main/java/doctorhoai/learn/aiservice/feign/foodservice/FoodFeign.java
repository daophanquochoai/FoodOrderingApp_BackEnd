package doctorhoai.learn.aiservice.feign.foodservice;


import doctorhoai.learn.aiservice.feign.config.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "foodservice", contextId = "invetoryFood", path = "/food", fallbackFactory = FoodFeignFallback.class, configuration = FeignConfig.class)
public interface FoodFeign {
    @PostMapping("/ids")
    ResponseEntity<ResponseObject> getAllIdsFood(
            @RequestBody List<Integer> ids
    );
}

