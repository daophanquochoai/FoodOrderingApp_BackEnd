package doctorhoai.learn.authservice.business.foodservice.service.foodsizeservice;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.FoodSizeDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodSizeFeignCFallback implements FallbackFactory<FoodSizeFeign> {

    private final HandleFallBack fallBack;

    @Override
    public FoodSizeFeign create(Throwable cause) {
        return new FoodSizeFeign() {
            @Override
            public ResponseEntity<ResponseObject> createNewFoodSize(FoodSizeDto foodSizeDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllFoodSize(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateFoodSize(FoodSizeDto foodSizeDto, Integer id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
