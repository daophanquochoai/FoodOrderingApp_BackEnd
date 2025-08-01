package doctorhoai.learn.orderservice.feign.foodservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "foodservice",
        path = "/voucher",
        contextId = "voucherOrder",
        fallbackFactory = VoucherFeignFallback.class,
        configuration = FeignConfig.class
)
public interface VoucherFeign {
    @GetMapping("/get/{id}")
    ResponseEntity<ResponseObject> getVoucherById(
            @PathVariable Integer id
    );
    @PostMapping("/get/mul")
    ResponseEntity<ResponseObject> getVoucherByMul(
            @RequestBody List<Integer> ids
    );
}
