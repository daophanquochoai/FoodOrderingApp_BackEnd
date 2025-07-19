package doctorhoai.learn.authservice.business.foodservice.controller;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.FoodDto;
import doctorhoai.learn.authservice.business.foodservice.service.foodservice.FoodFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodFeign foodFeign;

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllFood(
            @RequestBody Filter filter
    ){
        return foodFeign.getAllFood(filter);
    }
    @PostMapping("/ids")
    ResponseEntity<ResponseObject> getAllIdsFood(
            @RequestBody List<Integer> ids
    ){
        return foodFeign.getAllIdsFood(ids);
    }

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addFood(
            @RequestBody @Valid FoodDto dto
    ){
        return foodFeign.addFood(dto);
    }
    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateFood(
            @PathVariable Integer id,
            @RequestBody @Valid FoodDto dto
    ){
        return foodFeign.updateFood(id, dto);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getFood(@PathVariable Integer id){
        return foodFeign.getFood(id);
    }

    @PostMapping("/check_food")
    ResponseEntity<ResponseObject> checkFood(
            @RequestBody List<Integer> ids
    ){
        return foodFeign.checkFood(ids);
    }
}
