package doctorhoai.learn.orderservice.feign.inventory.ingredient_use;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientUseFeignFallback implements FallbackFactory<IngredientUseFeign> {
    private final HandleFallBack fallBack;

    @Override
    public IngredientUseFeign create(Throwable cause) {
        return new IngredientUseFeign() {
            @Override
            public ResponseEntity<ResponseObject> updateIngredientsInOrder(Integer orderId) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
