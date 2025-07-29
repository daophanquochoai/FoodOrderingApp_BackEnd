package doctorhoai.learn.authservice.business.orderservice.service.paymentservice;

import doctorhoai.learn.authservice.business.orderservice.model.Filter;
import doctorhoai.learn.authservice.business.orderservice.model.PaymentDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "orderservice",
        path = "/payment",
        contextId = "paymentBusiness",
        fallbackFactory = PaymentFeignFallback.class,
        configuration = FeignConfig.class
)
public interface PaymentFeign {
    @PostMapping("/add")
    ResponseEntity<ResponseObject> addPayment(
            @RequestBody @Valid PaymentDto paymentDto
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updatePayment(
            @PathVariable Integer id,
            @RequestBody @Valid PaymentDto paymentDto
    );

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllPayments(
            @RequestBody Filter filter
    );

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getPaymentById(
            @PathVariable Integer id
    );
}
