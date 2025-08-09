package doctorhoai.learn.foodservice.feign.ingredients;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "inventoryservice",
        path = "/ingredients",
        contextId = "ingredientsFood",
        fallbackFactory = IngredientFallback.class,
        configuration = FeignConfig.class
)
public interface IngredientFeign {
    @GetMapping("/ingredients")
    ResponseEntity<ResponseObject> getIngredients();
}
