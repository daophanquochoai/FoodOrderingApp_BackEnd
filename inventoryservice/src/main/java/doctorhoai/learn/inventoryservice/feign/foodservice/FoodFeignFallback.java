package doctorhoai.learn.inventoryservice.feign.foodservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.feign.function.HandleFallBack;
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
