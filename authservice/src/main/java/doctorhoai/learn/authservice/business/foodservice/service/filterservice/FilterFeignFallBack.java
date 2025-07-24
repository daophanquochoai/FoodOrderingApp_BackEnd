package doctorhoai.learn.authservice.business.foodservice.service.filterservice;

import doctorhoai.learn.authservice.business.foodservice.model.FilterOptions;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterFeignFallBack implements FallbackFactory<FilterFeign> {
    private final HandleFallBack fallBack;

    @Override
    public FilterFeign create(Throwable cause) {
        return new FilterFeign() {
            @Override
            public ResponseEntity<ResponseObject> getFilter(String state,FilterOptions filterOptions) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
