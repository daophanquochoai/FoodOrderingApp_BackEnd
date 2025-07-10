package doctorhoai.learn.foodservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.FoodSizeDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.service.foodsize.FoodSizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food_size")
@RequiredArgsConstructor
public class FoodSizeController {

    private final FoodSizeService foodSizeService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createNewFoodSize(
            @RequestBody @Valid FoodSizeDto foodSizeDto
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_FOOD_SIZE_SUCCESSFUL.getMessage())
                        .data(foodSizeService.createNewFoodSize(foodSizeDto))
                        .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getAllFoodSize(
            @RequestBody Filter filter
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_FOOD_SIZE_SUCCESSFUL.getMessage())
                        .data(foodSizeService.getAllFoodSize(filter))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateFoodSize(
            @RequestBody @Valid FoodSizeDto foodSizeDto,
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_FOOD_SIZE_SUCCESSFUL.getMessage())
                        .data(foodSizeService.updateFoodSize(foodSizeDto, id))
                        .build()
        );
    }
}
