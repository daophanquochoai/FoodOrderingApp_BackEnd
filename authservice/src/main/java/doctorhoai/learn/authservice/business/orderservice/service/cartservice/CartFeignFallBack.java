package doctorhoai.learn.authservice.business.orderservice.service.cartservice;

import doctorhoai.learn.authservice.business.orderservice.model.CartItemDto;
import doctorhoai.learn.authservice.business.orderservice.model.Filter;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartFeignFallBack implements FallbackFactory<CartFeign> {

    private final HandleFallBack fallBack;


    @Override
    public CartFeign create(Throwable cause) {
        return new CartFeign() {
            @Override
            public ResponseEntity<ResponseObject> removeCart(String username, Integer cartId) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllCarts(String username, Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> addCart(String username, CartItemDto cartItemDto) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
