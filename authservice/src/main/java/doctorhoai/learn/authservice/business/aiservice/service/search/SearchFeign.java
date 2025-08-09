package doctorhoai.learn.authservice.business.aiservice.service.search;

import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "aiservice",
        path = "/api/search",
        contextId = "searchSemanticFeignBusiness",
        fallbackFactory = SearchFeignFallback.class,
        configuration = FeignConfig.class
)
public interface SearchFeign {
    @GetMapping
    ResponseEntity<ResponseObject> searchByParam(
            @RequestParam String query
    );
}
