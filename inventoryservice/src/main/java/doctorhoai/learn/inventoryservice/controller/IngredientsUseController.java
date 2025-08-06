package doctorhoai.learn.inventoryservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.service.ingredients_use.IngredientsUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ingredients_use")
@RequiredArgsConstructor
public class IngredientsUseController {

    private final IngredientsUserService ingredientsUserService;

    @GetMapping("/get_ingredients/{orderId}")
    public ResponseEntity<ResponseObject> getIngredientsInOrder(
            @PathVariable Integer orderId
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_INGREDIENT_USE_SUCCESSFUL.getMessage())
                        .data(ingredientsUserService.getIngredientsByOrderId(orderId))
                        .build()
        );
    }

    @PostMapping("/update/{orderId}")
    public ResponseEntity<ResponseObject> updateIngredientsInOrder(
            @PathVariable Integer orderId
    ){
        ingredientsUserService.updateIngredientsUse(orderId);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_INGREDIETN_USE_SUCCESSFUL.getMessage())
                        .build()
        );
    }
}
