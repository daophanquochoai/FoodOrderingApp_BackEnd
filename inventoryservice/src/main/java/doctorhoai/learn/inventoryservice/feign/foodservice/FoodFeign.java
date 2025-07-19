package doctorhoai.learn.inventoryservice.feign.foodservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "foodservice", contextId = "invetoryFood", path = "/food", fallbackFactory = FoodFeignFallback.class, configuration = FeignConfig.class)
public interface FoodFeign {
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getFood(@PathVariable Integer id);

    @PostMapping("/check_food")
    ResponseEntity<ResponseObject> checkFood(
            @RequestBody List<Integer> ids
    );
}
