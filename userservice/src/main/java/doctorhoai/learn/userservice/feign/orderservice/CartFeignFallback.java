package doctorhoai.learn.userservice.feign.orderservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.userservice.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartFeignFallback implements FallbackFactory<CartFeign> {


    private final HandleFallBack fallBack;

    @Override
    public CartFeign create(Throwable cause) {
        return new CartFeign() {
            @Override
            public ResponseEntity<ResponseObject> createCart(Integer userId) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
