package doctorhoai.learn.inventoryservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.dto.orderservice.OrderItemDto;
import doctorhoai.learn.inventoryservice.service.ingredients_use.IngredientsUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history_ingredients")
@RequiredArgsConstructor
public class HistoryIngredientsController {

    private final IngredientsUserService ingredientsUserService;

    @PostMapping("/checkOrder")
    private ResponseEntity<ResponseObject> checkOrder(
            @RequestBody List<OrderItemDto> orderItemDtoList
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CHECK_ORDER_SUCCESSFUL.getMessage())
                        .data(ingredientsUserService.afterBookFood(orderItemDtoList))
                        .build()
        );
    }
}
