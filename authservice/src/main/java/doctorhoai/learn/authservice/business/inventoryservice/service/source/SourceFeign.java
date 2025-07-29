package doctorhoai.learn.authservice.business.inventoryservice.service.source;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.SourceDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "inventoryservice",
        path = "/source",
        contextId = "sourceBusiness",
        fallbackFactory = SourceFeignFallback.class,
        configuration = FeignConfig.class
)
public interface SourceFeign {
    @PostMapping("/add")
    ResponseEntity<ResponseObject> addSource(
            @RequestBody @Valid SourceDto sourceDto
    );

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllSource(
            @RequestBody Filter filter
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateSource(
            @PathVariable Integer id,
            @RequestBody @Valid SourceDto sourceDto
    );
}
