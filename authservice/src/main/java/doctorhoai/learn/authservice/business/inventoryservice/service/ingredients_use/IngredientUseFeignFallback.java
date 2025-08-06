package doctorhoai.learn.authservice.business.inventoryservice.service.ingredients_use;

import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientUseFeignFallback implements FallbackFactory<IngredientsUseFeign> {

    private final HandleFallBack fallBack;

    @Override
    public IngredientsUseFeign create(Throwable cause) {
        return new IngredientsUseFeign() {
            @Override
            public ResponseEntity<ResponseObject> getIngredientsInOrder(Integer orderId) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
