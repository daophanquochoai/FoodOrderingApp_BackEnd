package doctorhoai.learn.authservice.business.aiservice.service.search;

import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchFeignFallback implements FallbackFactory<SearchFeign> {

    private final HandleFallBack fallBack;

    @Override
    public SearchFeign create(Throwable cause) {
        return new SearchFeign() {
            @Override
            public ResponseEntity<ResponseObject> searchByParam(String query) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
