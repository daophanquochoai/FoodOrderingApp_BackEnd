package doctorhoai.learn.authservice.business.inventoryservice.service.filter;

import doctorhoai.learn.authservice.business.inventoryservice.model.FilterOption;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterFeignFallback implements FallbackFactory<FilterFeign> {

    private final HandleFallBack fallBack;


    @Override
    public FilterFeign create(Throwable cause) {
        return new FilterFeign() {

            @Override
            public ResponseEntity<ResponseObject> getFilterByAll(FilterOption filterOption) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
