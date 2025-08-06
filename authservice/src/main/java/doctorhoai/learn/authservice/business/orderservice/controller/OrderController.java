package doctorhoai.learn.authservice.business.orderservice.controller;

import doctorhoai.learn.authservice.business.orderservice.model.Filter;
import doctorhoai.learn.authservice.business.orderservice.model.OrderDto;
import doctorhoai.learn.authservice.business.orderservice.model.UpdateStatusOrder;
import doctorhoai.learn.authservice.business.orderservice.service.orderservice.OrderFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFeign orderFeign;

    @PostMapping("/order")
    ResponseEntity<ResponseObject> order(
            @RequestBody OrderDto orderDto
    ){
        return orderFeign.order(orderDto);
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> all(
            @RequestBody Filter filter
    )
    {
        return orderFeign.all(filter);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<ResponseObject> changeStatusOrder(
            @PathVariable Integer id,
            @RequestBody UpdateStatusOrder statusOrder
    ){
        return orderFeign.changeStatusOrder(id, statusOrder);
    }
}
