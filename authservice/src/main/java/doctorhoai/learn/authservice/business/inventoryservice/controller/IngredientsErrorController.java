package doctorhoai.learn.authservice.business.inventoryservice.controller;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.IngredientsErrorDto;
import doctorhoai.learn.authservice.business.inventoryservice.service.ingredients_error.IngredientsErrorFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients_error")
@RequiredArgsConstructor
public class IngredientsErrorController {

    private final IngredientsErrorFeign ingredientsErrorFeign;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createIngredients(
            @RequestBody @Valid IngredientsErrorDto ingredientsErrorDto
    ){
        return ingredientsErrorFeign.createIngredients(ingredientsErrorDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateIngredients(
            @RequestBody @Valid IngredientsErrorDto ingredientsErrorDto,
            @PathVariable Integer id
    ){
        return ingredientsErrorFeign.updateIngredients(ingredientsErrorDto, id);
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getAllIngredients(
            @RequestBody Filter filter
    ) {
        return ingredientsErrorFeign.getAllIngredients(filter);
    }

    @GetMapping("/get_ingredients/{historyId}")
    public ResponseEntity<ResponseObject> getHistoryIngredientsByHistoryId(@PathVariable Integer historyId) {
        return ingredientsErrorFeign.getHistoryIngredientsByHistoryId(historyId);
    }
}
