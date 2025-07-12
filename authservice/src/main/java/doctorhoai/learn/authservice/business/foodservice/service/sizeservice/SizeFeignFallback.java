package doctorhoai.learn.authservice.business.foodservice.service.sizeservice;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.SizeDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SizeFeignFallback implements FallbackFactory<SizeFeign> {

    private final HandleFallBack fallBack;


    @Override
    public SizeFeign create(Throwable cause) {
        return new SizeFeign() {
            @Override
            public ResponseEntity<ResponseObject> createNewSize(SizeDto sizeDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateSize(Integer id, SizeDto sizeDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllSize(Filter filter) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
