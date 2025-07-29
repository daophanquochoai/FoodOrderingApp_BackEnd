package doctorhoai.learn.userservice.feign.orderservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.userservice.feign.function.HandleFallBack;
import doctorhoai.learn.userservice.feign.model.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointFeignFallback implements FallbackFactory<PointFeign> {

    private final HandleFallBack fallBack;

    @Override
    public PointFeign create(Throwable cause) {
        return new PointFeign() {
            @Override
            public ResponseEntity<ResponseObject> createPoint(PointDto point) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
