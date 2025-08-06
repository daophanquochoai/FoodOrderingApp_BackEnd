package doctorhoai.learn.authservice.business.inventoryservice.controller;

import doctorhoai.learn.authservice.business.inventoryservice.service.ingredients_use.IngredientsUseFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ingredients_use")
@RequiredArgsConstructor
public class IngredientsUseController {
    private final IngredientsUseFeign ingredientsUseFeign;

    @GetMapping("/get_ingredients/{orderId}")
    public ResponseEntity<ResponseObject> getIngredientsInOrder(
            @PathVariable Integer orderId
    ){
        return ingredientsUseFeign.getIngredientsInOrder(orderId);
    }
}
