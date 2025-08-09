package doctorhoai.learn.foodservice.feign.ingredients;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientFallback implements FallbackFactory<IngredientFeign> {

    private final HandleFallBack fallBack;

    @Override
    public IngredientFeign create(Throwable cause) {
        return new IngredientFeign() {
            @Override
            public ResponseEntity<ResponseObject> getIngredients() {
                return fallBack.processFallback(cause);
            }
        };
    }
}
