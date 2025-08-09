package doctorhoai.learn.authservice.business.foodservice.service.foodhomepage;

import doctorhoai.learn.authservice.business.foodservice.model.FoodHomepageDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodHomePageFeignFallback implements FallbackFactory<FoodHomePageFeign> {

    private final HandleFallBack fallBack;

    @Override
    public FoodHomePageFeign create(Throwable cause) {
        return new FoodHomePageFeign() {
            @Override
            public ResponseEntity<ResponseObject> getLatestFoods() {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getDealOfWeekFoods() {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> addFoodHomepage(FoodHomepageDto foodHomepageDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteCategoryHomepage(Integer id, FoodHomepageDto foodHomepageDto) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
