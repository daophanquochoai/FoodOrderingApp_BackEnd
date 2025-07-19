package doctorhoai.learn.authservice.business.foodservice.controller;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.FoodSizeDto;
import doctorhoai.learn.authservice.business.foodservice.service.foodsizeservice.FoodSizeFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food_size")
@RequiredArgsConstructor
public class FoodSizeController {

    private final FoodSizeFeign foodSizeFeign;

    @PostMapping("/add")
    ResponseEntity<ResponseObject> createNewFoodSize(
            @RequestBody @Valid FoodSizeDto foodSizeDto
    ){
        return foodSizeFeign.createNewFoodSize(foodSizeDto);
    }

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllFoodSize(
            @RequestBody Filter filter
    ){
        return foodSizeFeign.getAllFoodSize(filter);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateFoodSize(
            @RequestBody @Valid FoodSizeDto foodSizeDto,
            @PathVariable Integer id
    ){
        return foodSizeFeign.updateFoodSize(foodSizeDto, id);
    }
}
