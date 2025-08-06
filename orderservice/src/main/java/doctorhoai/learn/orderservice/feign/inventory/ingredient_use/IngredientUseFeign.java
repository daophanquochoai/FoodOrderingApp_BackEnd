package doctorhoai.learn.orderservice.feign.inventory.ingredient_use;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "inventoryservice",
        path = "/ingredients_use",
        contextId = "ingredientsAndOrder",
        fallbackFactory = IngredientUseFeignFallback.class,
        configuration = FeignConfig.class
)
public interface IngredientUseFeign {
    @PostMapping("/update/{orderId}")
    ResponseEntity<ResponseObject> updateIngredientsInOrder(
        @PathVariable Integer orderId
    );
}
