package doctorhoai.learn.authservice.business.inventoryservice.service.filter;

import doctorhoai.learn.authservice.business.inventoryservice.model.FilterOption;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "inventoryservice",
        path = "/filter",
        contextId = "filterHistoryBusiness",
        fallbackFactory = FilterFeignFallback.class,
        configuration = FeignConfig.class
)
public interface FilterFeign {
    @PostMapping("/all")
    ResponseEntity<ResponseObject> getFilterByAll(
            @RequestBody FilterOption filterOption
    );
}
