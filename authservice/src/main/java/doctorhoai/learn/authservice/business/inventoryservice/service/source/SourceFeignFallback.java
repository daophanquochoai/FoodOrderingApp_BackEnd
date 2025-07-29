package doctorhoai.learn.authservice.business.inventoryservice.service.source;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.SourceDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SourceFeignFallback implements FallbackFactory<SourceFeign> {

    private final HandleFallBack fallBack;

    @Override
    public SourceFeign create(Throwable cause) {
        return new SourceFeign() {
            @Override
            public ResponseEntity<ResponseObject> addSource(SourceDto sourceDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllSource(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateSource(Integer id, SourceDto sourceDto) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
