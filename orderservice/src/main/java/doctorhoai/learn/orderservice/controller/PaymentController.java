package doctorhoai.learn.orderservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.dto.PaymentDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;
import doctorhoai.learn.orderservice.service.paymentservice.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

     @PostMapping("/add")
    public ResponseEntity<ResponseObject> addPayment(
             @RequestBody @Valid PaymentDto paymentDto
             )
     {
         return ResponseEntity.ok(
                 ResponseObject.builder()
                         .message(EMessageResponse.CREATE_PAYMENT_SUCCESSFUL.getMessage())
                         .data(paymentService.addPayment(paymentDto))
                         .build()
         );
     }

     @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updatePayment(
            @PathVariable Integer id,
            @RequestBody @Valid PaymentDto paymentDto
     ){
         return ResponseEntity.ok(
                 ResponseObject.builder()
                         .message(EMessageResponse.UPDATE_PAYMENT_SUCCESSFUL.getMessage())
                         .data(paymentService.updatePayment(paymentDto, id))
                         .build()
         );
     }

     @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllPayments(
             @RequestBody Filter filter
             ) {
         return ResponseEntity.ok(
                 ResponseObject.builder()
                         .data(paymentService.getPaymentsByFilter(filter))
                         .message(EMessageResponse.GET_PAYMENT_SUCCESSFUL.getMessage())
                         .build()
         );
     }

     @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getPaymentById(
            @PathVariable Integer id
     ){
         return ResponseEntity.ok(
                 ResponseObject.builder()
                         .message(EMessageResponse.GET_PAYMENT_SUCCESSFUL.getMessage())
                         .data(paymentService.getPaymentById(id))
                         .build()
         );
     }
}
