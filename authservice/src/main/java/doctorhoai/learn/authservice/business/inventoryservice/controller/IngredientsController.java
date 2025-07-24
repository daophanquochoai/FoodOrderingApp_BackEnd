package doctorhoai.learn.authservice.business.inventoryservice.controller;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.IngredientsDto;
import doctorhoai.learn.authservice.business.inventoryservice.service.ingredients.IngredientFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientsController {

    private final IngredientFeign ingredientFeign;

    @PostMapping("/create")
    ResponseEntity<ResponseObject> createIngredients(
            @RequestBody @Valid IngredientsDto ingredientsDto
    ){
        return ingredientFeign.createIngredients(ingredientsDto);
    }

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllIngredients(
            @RequestBody Filter filter
    ){
        return ingredientFeign.getAllIngredients(filter);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getIngredients(
            @PathVariable Integer id
    ){
        return ingredientFeign.getIngredients(id);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateIngredients(
            @RequestBody @Valid IngredientsDto ingredientsDto,
            @PathVariable Integer id
    ){
        return ingredientFeign.updateIngredients(ingredientsDto, id);
    }
    @PostMapping("/history/{id}")
    public ResponseEntity<ResponseObject> getHistoryIngredients(
            @PathVariable Integer id,
            @RequestBody Filter filter
    ){
        return ingredientFeign.getHistoryIngredients(id, filter);
    }
}
