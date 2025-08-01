package doctorhoai.learn.authservice.business.orderservice.service.orderservice;

import doctorhoai.learn.authservice.business.orderservice.model.Filter;
import doctorhoai.learn.authservice.business.orderservice.model.OrderDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderFeignFallback implements FallbackFactory<OrderFeign> {

    private final HandleFallBack fallBack;

    @Override
    public OrderFeign create(Throwable cause) {
        return new OrderFeign() {
            @Override
            public ResponseEntity<ResponseObject> order(OrderDto orderDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> all(Filter filter) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
