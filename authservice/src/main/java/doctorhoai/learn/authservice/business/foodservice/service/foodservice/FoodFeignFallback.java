package doctorhoai.learn.authservice.business.foodservice.service.foodservice;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.FoodDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FoodFeignFallback implements FallbackFactory<FoodFeign> {

    private final HandleFallBack fallBack;

    @Override
    public FoodFeign create(Throwable cause) {
        return new FoodFeign() {
            @Override
            public ResponseEntity<ResponseObject> getAllFood(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllIdsFood(List<Integer> ids) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> addFood(FoodDto dto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateFood(Integer id, FoodDto dto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getFood(Integer id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> checkFood(List<Integer> ids) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
