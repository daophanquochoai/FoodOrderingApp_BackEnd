package doctorhoai.learn.authservice.business.inventoryservice.service.ingredients;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.IngredientsDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientsFeignFallBack implements FallbackFactory<IngredientFeign> {

    private final HandleFallBack fallBack;

    @Override
    public IngredientFeign create(Throwable cause) {
        return new IngredientFeign() {
            @Override
            public ResponseEntity<ResponseObject> createIngredients(IngredientsDto ingredientsDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllIngredients(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getIngredients(Integer id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateIngredients(IngredientsDto ingredientsDto, Integer id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getHistoryIngredients(Integer id, Filter filter) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
