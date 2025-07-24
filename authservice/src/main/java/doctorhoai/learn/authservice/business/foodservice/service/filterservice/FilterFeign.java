package doctorhoai.learn.authservice.business.foodservice.service.filterservice;

import doctorhoai.learn.authservice.business.foodservice.model.FilterOptions;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "foodservice",
        path = "/filter",
        fallbackFactory = FilterFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface FilterFeign {
    @PostMapping("/all/{state}")
    ResponseEntity<ResponseObject> getFilter(
            @PathVariable String state,
            @RequestBody FilterOptions filterOptions
    );
}
