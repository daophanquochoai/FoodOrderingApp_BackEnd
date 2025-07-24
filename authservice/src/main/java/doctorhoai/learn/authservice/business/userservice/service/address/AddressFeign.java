package doctorhoai.learn.authservice.business.userservice.service.address;

import doctorhoai.learn.authservice.business.userservice.model.AddressDto;
import doctorhoai.learn.authservice.business.userservice.model.Filter;
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
        name = "userservice",
        path = "/address",
        contextId = "addressFeignBusiness",
        fallbackFactory = AddressFeignFallback.class,
        configuration = FeignConfig.class
)
public interface AddressFeign {
    @PostMapping("/add")
    ResponseEntity<ResponseObject> createAddress(
            @RequestBody @Valid AddressDto addressDto
    );

    @PostMapping("/all")
    ResponseEntity<ResponseObject> createAllAddress(
            @RequestBody Filter filter
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateAddress(
            @PathVariable Integer id,
            @RequestBody @Valid AddressDto addressDto
    );
}
