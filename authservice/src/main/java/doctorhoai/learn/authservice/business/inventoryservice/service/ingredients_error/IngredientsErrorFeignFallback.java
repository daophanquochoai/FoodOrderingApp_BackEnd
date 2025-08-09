package doctorhoai.learn.authservice.business.inventoryservice.service.ingredients_error;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.IngredientsErrorDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientsErrorFeignFallback implements FallbackFactory<IngredientsErrorFeign> {
    private final HandleFallBack fallBack;

    @Override
    public IngredientsErrorFeign create(Throwable cause) {
        return new IngredientsErrorFeign() {
            @Override
            public ResponseEntity<ResponseObject> createIngredients(IngredientsErrorDto ingredientsErrorDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateIngredients(IngredientsErrorDto ingredientsErrorDto, Integer id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllIngredients(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getHistoryIngredientsByHistoryId(Integer historyId) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
