package doctorhoai.learn.authservice.business.foodservice.controller;

import doctorhoai.learn.authservice.business.foodservice.model.FoodHomepageDto;
import doctorhoai.learn.authservice.business.foodservice.service.foodhomepage.FoodHomePageFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food-homepage")
@RequiredArgsConstructor
public class FoodHomePageController {

    private final FoodHomePageFeign foodHomePageFeign;

    @GetMapping("/all-latest")
    ResponseEntity<ResponseObject> getLatestFoods(){
        return foodHomePageFeign.getLatestFoods();
    }

    @GetMapping("/all-deal-of-week")
    ResponseEntity<ResponseObject> getDealOfWeekFoods(){
        return foodHomePageFeign.getDealOfWeekFoods();
    }

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addFoodHomepage(
            @RequestBody FoodHomepageDto foodHomepageDto
    ){
        return foodHomePageFeign.addFoodHomepage(foodHomepageDto);
    }

    @PutMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteCategoryHomepage (
            @PathVariable Integer id,
            @RequestBody FoodHomepageDto foodHomepageDto
    ){
        return foodHomePageFeign.deleteCategoryHomepage(id, foodHomepageDto);
    }
}
