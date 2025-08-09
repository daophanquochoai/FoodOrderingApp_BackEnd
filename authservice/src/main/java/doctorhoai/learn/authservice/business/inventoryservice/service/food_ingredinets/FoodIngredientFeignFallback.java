package doctorhoai.learn.authservice.business.inventoryservice.service.food_ingredinets;

import doctorhoai.learn.authservice.business.inventoryservice.model.FoodIngredientsDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FoodIngredientFeignFallback implements FallbackFactory<FoodIngredientFeign> {

    private final HandleFallBack fallBack;

    @Override
    public FoodIngredientFeign create(Throwable cause) {
        return new FoodIngredientFeign() {
            @Override
            public ResponseEntity<ResponseObject> addFoodIngredients(List<FoodIngredientsDto> foodIngredientsDtos, Integer id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateFoodIngredients(Integer id, List<FoodIngredientsDto> foodIngredientsDtos) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
