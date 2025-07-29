package doctorhoai.learn.authservice.business.orderservice.controller;

import doctorhoai.learn.authservice.business.orderservice.model.Filter;
import doctorhoai.learn.authservice.business.orderservice.model.PaymentDto;
import doctorhoai.learn.authservice.business.orderservice.service.paymentservice.PaymentFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFeign paymentFeign;

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addPayment(
            @RequestBody @Valid PaymentDto paymentDto
    ){
        return paymentFeign.addPayment(paymentDto);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updatePayment(
            @PathVariable Integer id,
            @RequestBody @Valid PaymentDto paymentDto
    ){
        return paymentFeign.updatePayment(id, paymentDto);
    }

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllPayments(
            @RequestBody Filter filter
    ){
        return paymentFeign.getAllPayments(filter);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getPaymentById(
            @PathVariable Integer id
    ){
        return paymentFeign.getPaymentById(id);
    }
}
