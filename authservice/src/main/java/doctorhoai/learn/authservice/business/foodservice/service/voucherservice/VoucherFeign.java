package doctorhoai.learn.authservice.business.foodservice.service.voucherservice;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.VoucherDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "foodservice",
        path = "/voucher",
        contextId = "voucherFeignBusiness",
        fallbackFactory = VoucherFeignFallback.class,
        configuration = FeignConfig.class
)
public interface VoucherFeign {
    @GetMapping("/user")
    ResponseEntity<ResponseObject> getVoucherOfUser(
            @RequestBody Filter filter
    );

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addVoucher(@RequestBody @Valid VoucherDto voucherDto);

    @GetMapping("/all")
    ResponseEntity<ResponseObject> getAllVoucher(
            @RequestBody Filter filter
    );

    @GetMapping("/{code}")
    ResponseEntity<ResponseObject> getVoucherByCode(
            @PathVariable String code
    );
}
