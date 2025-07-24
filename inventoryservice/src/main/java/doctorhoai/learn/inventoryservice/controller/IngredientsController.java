package doctorhoai.learn.inventoryservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.dto.IngredientsDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;
import doctorhoai.learn.inventoryservice.service.history_import_or_export.HistoryImportOrExportService;
import doctorhoai.learn.inventoryservice.service.ingredients.IngredientsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientsController {

    private final IngredientsService ingredientsService;
    private final HistoryImportOrExportService historyImportOrExportService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createIngredients(
            @RequestBody @Valid IngredientsDto ingredientsDto
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_INGREDIENTS_SUCCESSFUL.getMessage())
                        .data(ingredientsService.createIngredients(ingredientsDto))
                        .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getAllIngredients(
            @RequestBody Filter filter
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_INGREDIENTS_SUCCESSFUL.getMessage())
                        .data(ingredientsService.getAllIngredients(filter))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getIngredients(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_INGREDIENTS_SUCCESSFUL.getMessage())
                        .data(ingredientsService.getIngredients(id))
                        .build()
        );
    }

    @PostMapping("/history/{id}")
    public ResponseEntity<ResponseObject> getHistoryIngredients(
            @PathVariable Integer id,
            @RequestBody Filter filter
    ){
        List<Integer> ingredients = List.of(id);
        filter.setIngredientsId(ingredients);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_HISTORY_IMPORT_OR_EXPORT.getMessage())
                        .data(historyImportOrExportService.getHistoryImportOrExportByFilter(filter))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateIngredients(
            @RequestBody @Valid IngredientsDto ingredientsDto,
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_INGREDIENTS_SUCCESSFUL.getMessage())
                        .data(ingredientsService.updateIngredients(ingredientsDto, id))
                        .build()
        );
    }
}
