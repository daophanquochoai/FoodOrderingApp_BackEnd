package doctorhoai.learn.orderservice.feign.foodservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodSizeFeignFallback implements FallbackFactory<FoodSizeFeign> {

    private final HandleFallBack fallBack;

    @Override
    public FoodSizeFeign create(Throwable cause) {
        return new FoodSizeFeign() {
            @Override
            public ResponseEntity<ResponseObject> getFoodSize(Integer id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
