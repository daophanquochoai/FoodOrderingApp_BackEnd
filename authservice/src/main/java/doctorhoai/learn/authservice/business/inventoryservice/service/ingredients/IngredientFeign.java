package doctorhoai.learn.authservice.business.inventoryservice.service.ingredients;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.IngredientsDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(
        name = "inventoryservice",
        path = "/ingredients",
        contextId = "ingredientsBusiness",
        fallbackFactory = IngredientsFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface IngredientFeign {
    @PostMapping("/create")
    ResponseEntity<ResponseObject> createIngredients(
            @RequestBody @Valid IngredientsDto ingredientsDto
    );

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllIngredients(
            @RequestBody Filter filter
    );

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getIngredients(
            @PathVariable Integer id
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateIngredients(
            @RequestBody @Valid IngredientsDto ingredientsDto,
            @PathVariable Integer id
    );
    @PostMapping("/history/{id}")
    ResponseEntity<ResponseObject> getHistoryIngredients(
            @PathVariable Integer id,
            @RequestBody Filter filter
    );

    @PostMapping("/food_size/{id}")
    ResponseEntity<ResponseObject> getIngredientsFoodSize(
            @PathVariable Integer id
    );
}
