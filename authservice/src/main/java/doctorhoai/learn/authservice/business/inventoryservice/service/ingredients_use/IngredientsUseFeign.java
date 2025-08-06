package doctorhoai.learn.authservice.business.inventoryservice.service.ingredients_use;

import doctorhoai.learn.authservice.business.inventoryservice.service.ingredients.IngredientsFeignFallBack;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "inventoryservice",
        path = "/ingredients_use",
        contextId = "ingredientsUseBusiness",
        fallbackFactory = IngredientsFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface IngredientsUseFeign {
    @GetMapping("/get_ingredients/{orderId}")
    ResponseEntity<ResponseObject> getIngredientsInOrder(
            @PathVariable Integer orderId
    );
}