package doctorhoai.learn.inventoryservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.dto.FoodIngredientsDto;
import doctorhoai.learn.inventoryservice.service.food_ingredients.FoodIngredientsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food_ingredients")
@RequiredArgsConstructor
public class FoodIngredientsController {

    private final FoodIngredientsService foodIngredientsService;

    @GetMapping("/food/{id}")
    public ResponseEntity<ResponseObject> getIngredientsOfFood(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_FOOD_INGREDIENTS_SUCCESSFUL.getMessage())
                        .data(foodIngredientsService.getFoodIngredientsDtoByFoodId(id))
                        .build()
        );
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<ResponseObject> addFoodIngredients(
            @RequestBody @Valid List<FoodIngredientsDto> foodIngredientsDtos,
            @PathVariable Integer id
            )
    {
        foodIngredientsService.createFoodIngredients(foodIngredientsDtos, id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_FOOD_INGREDIENTS_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateFoodIngredients(
            @PathVariable Integer id,
            @RequestBody @Valid List<FoodIngredientsDto> foodIngredientsDtos
    ){
        foodIngredientsService.updateFoodIngredients(foodIngredientsDtos, id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_FOOD_INGREDIENTS_SUCCESSFUL.getMessage())
                        .build()
        );
    }
}
