package doctorhoai.learn.inventoryservice.feign.foodservice;


import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "foodservice", contextId = "foodSizeIngredients", path = "/food_size", fallbackFactory = FoodFeignFallback.class, configuration = FeignConfig.class)
public interface FoodSizeFeign {
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getFoodSize(
            @PathVariable Integer id
    );
}
