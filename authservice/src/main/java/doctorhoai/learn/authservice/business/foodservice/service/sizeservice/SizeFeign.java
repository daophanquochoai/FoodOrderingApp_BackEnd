package doctorhoai.learn.authservice.business.foodservice.service.sizeservice;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.SizeDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "foodservice",
        path = "/size",
        contextId = "sizeFeignBusiness",
        configuration = FeignConfig.class,
        fallbackFactory = SizeFeignFallback.class
)
public interface SizeFeign {

    @PostMapping("/add")
    ResponseEntity<ResponseObject> createNewSize(
            @RequestBody @Valid SizeDto sizeDto
    );
    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateSize(
            @PathVariable Integer id,
            @RequestBody @Valid SizeDto sizeDto
    );

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllSize(
            @RequestBody Filter filter
    );
}
