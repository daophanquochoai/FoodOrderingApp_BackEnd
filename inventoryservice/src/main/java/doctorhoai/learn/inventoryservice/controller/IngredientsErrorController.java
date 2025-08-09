package doctorhoai.learn.inventoryservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.dto.IngredientsErrorDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;
import doctorhoai.learn.inventoryservice.service.ingredients_error.IngredientsErrorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients_error")
@RequiredArgsConstructor
public class IngredientsErrorController {

    private final IngredientsErrorService ingredientsErrorService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createIngredients(
            @RequestBody @Valid IngredientsErrorDto ingredientsErrorDto
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_INGREDIENTS_ERROR_SUCCESSFUL.getMessage())
                        .data(ingredientsErrorService.createIngredientsError(ingredientsErrorDto))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateIngredients(
            @RequestBody @Valid IngredientsErrorDto ingredientsErrorDto,
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_INGREDIENTS_ERROR_SUCCESSFUL.getMessage())
                        .data(ingredientsErrorService.updateIngredientsError(ingredientsErrorDto, id))
                        .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getAllIngredients(
            @RequestBody Filter filter
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_INGREDIENTS_ERROR_SUCCESSFUL.getMessage())
                        .data(ingredientsErrorService.getIngredientsErrorPage(filter))
                        .build()
        );
    }

    @GetMapping("/get_ingredients/{historyId}")
    public ResponseEntity<ResponseObject> getHistoryIngredientsByHistoryId(@PathVariable Integer historyId) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Get History Ingredients successful")
                        .data(ingredientsErrorService.getHistoryIngredientsByHistoryId(historyId))
                        .build()
        );
    }
}
