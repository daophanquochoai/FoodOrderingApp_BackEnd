package doctorhoai.learn.authservice.business.inventoryservice.service.food_ingredinets;

import doctorhoai.learn.authservice.business.inventoryservice.model.FoodIngredientsDto;
import doctorhoai.learn.authservice.business.inventoryservice.service.history_ingredients.HistoryFeignFallback;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "inventoryservice",
        path = "/food_ingredients",
        contextId = "foodIngredientsBusiness",
        fallbackFactory = HistoryFeignFallback.class,
        configuration = FeignConfig.class
)
public interface FoodIngredientFeign {
    @PostMapping("/add/{id}")
    ResponseEntity<ResponseObject> addFoodIngredients(
            @RequestBody @Valid List<FoodIngredientsDto> foodIngredientsDtos,
            @PathVariable Integer id
    );
    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateFoodIngredients(
            @PathVariable Integer id,
            @RequestBody List<FoodIngredientsDto> foodIngredientsDtos
    );
}
