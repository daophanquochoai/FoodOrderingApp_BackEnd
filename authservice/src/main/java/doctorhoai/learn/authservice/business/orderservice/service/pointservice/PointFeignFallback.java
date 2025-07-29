package doctorhoai.learn.authservice.business.orderservice.service.pointservice;

import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
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
            public ResponseEntity<ResponseObject> getPointOfUser(int userId) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
