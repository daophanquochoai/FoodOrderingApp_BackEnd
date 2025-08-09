package doctorhoai.learn.authservice.business.inventoryservice.controller;

import doctorhoai.learn.authservice.business.inventoryservice.model.FoodIngredientsDto;
import doctorhoai.learn.authservice.business.inventoryservice.service.food_ingredinets.FoodIngredientFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food_ingredients")
@RequiredArgsConstructor
public class FoodIngredientsController {

    private final FoodIngredientFeign foodIngredientFeign;

    @PostMapping("/add/{id}")
    public ResponseEntity<ResponseObject> addFoodIngredients(
            @RequestBody @Valid List<FoodIngredientsDto> foodIngredientsDtos,
            @PathVariable Integer id
    ){
        return foodIngredientFeign.addFoodIngredients(foodIngredientsDtos, id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateFoodIngredients(
            @PathVariable Integer id,
            @RequestBody List<FoodIngredientsDto> foodIngredientsDtos
    ){
            return foodIngredientFeign.updateFoodIngredients(id, foodIngredientsDtos);
    }
}
