package doctorhoai.learn.authservice.business.inventoryservice.service.ingredients_error;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.IngredientsErrorDto;
import doctorhoai.learn.authservice.business.inventoryservice.service.ingredients.IngredientsFeignFallBack;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "inventoryservice",
        path = "/ingredients_error",
        contextId = "ingredientsErrorBusiness",
        fallbackFactory = IngredientsFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface IngredientsErrorFeign {
    @PostMapping("/add")
    ResponseEntity<ResponseObject> createIngredients(
            @RequestBody @Valid IngredientsErrorDto ingredientsErrorDto
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateIngredients(
            @RequestBody @Valid IngredientsErrorDto ingredientsErrorDto,
            @PathVariable Integer id
    );

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllIngredients(
            @RequestBody Filter filter
    );

    @GetMapping("/get_ingredients/{historyId}")
    ResponseEntity<ResponseObject> getHistoryIngredientsByHistoryId(@PathVariable Integer historyId);
}
