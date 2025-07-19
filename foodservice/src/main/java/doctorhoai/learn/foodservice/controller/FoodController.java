package doctorhoai.learn.foodservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.service.food.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getAllFood(
            @RequestBody Filter filter
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodService.getFoodByFilter(filter))
                        .message(EMessageResponse.GET_FOOD.getMessage())
                        .build()
        );
    }
    @GetMapping("/food/all")
    public ResponseEntity<ResponseObject> getAllFoodNoFilter(
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodService.getAll())
                        .message(EMessageResponse.GET_FOOD.getMessage())
                        .build()
        );
    }

    @PostMapping("/ids")
    public ResponseEntity<ResponseObject> getAllIdsFood(
            @RequestBody List<Integer> ids
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodService.getAllIdsFood(ids))
                        .message(EMessageResponse.GET_FOOD.getMessage())
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addFood(
            @RequestBody @Valid FoodDto dto
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodService.createFood(dto))
                        .message(EMessageResponse.CREATE_FOOD.getMessage())
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateFood(
            @PathVariable Integer id,
            @RequestBody @Valid FoodDto dto
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodService.updateFood(dto, id))
                        .message(EMessageResponse.UPDATE_FOOD.getMessage())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getFood(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodService.getFoodById(id))
                        .message(EMessageResponse.GET_FOOD.getMessage())
                        .build()
        );
    }

    @PostMapping("/check_food")
    public ResponseEntity<ResponseObject> checkFood(
            @RequestBody List<Integer> ids
            ){
        foodService.checkFood(ids);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.FOOD_FOUND.getMessage())
                        .build()
        );
    }
}
