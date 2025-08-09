package doctorhoai.learn.orderservice.controller;

import com.stripe.exception.StripeException;
import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.dto.OrderDto;
import doctorhoai.learn.orderservice.dto.UpdateStatusOrder;
import doctorhoai.learn.orderservice.dto.filter.Filter;
import doctorhoai.learn.orderservice.model.enums.EStatusOrder;
import doctorhoai.learn.orderservice.service.orderservice.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ResponseObject> order(
            @RequestBody OrderDto orderDto
            ) throws StripeException {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.ORDER_SUCCESSFUL.getMessage())
                        .data(orderService.save(orderDto))
                        .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> all(
            @RequestBody Filter filter
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_ORDER_SUCCESSFUL.getMessage())
                        .data(orderService.getOrderByFilter(filter))
                        .build()
        );
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<ResponseObject> changeStatusOrder(
            @PathVariable Integer id,
            @RequestBody UpdateStatusOrder statusOrder
            )
    {
        orderService.updateStatusOrder(id, statusOrder);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CHANGE_ORDER_SUCCESSFUL.getMessage())
                        .build()
        );
    }

}
