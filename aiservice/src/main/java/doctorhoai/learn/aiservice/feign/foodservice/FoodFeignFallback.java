package doctorhoai.learn.aiservice.feign.foodservice;

import doctorhoai.learn.aiservice.feign.function.HandleFallBack;
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
            public ResponseEntity<ResponseObject> getAllFoodNoFilter() {
                return fallBack.processFallback(cause);
            }
        };
    }
}
