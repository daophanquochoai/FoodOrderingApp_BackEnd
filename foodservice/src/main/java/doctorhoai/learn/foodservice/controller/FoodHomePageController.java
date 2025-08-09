package doctorhoai.learn.foodservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.FoodHomepageDto;
import doctorhoai.learn.foodservice.service.foodhomepage.FoodHomepageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food-homepage")
@RequiredArgsConstructor
public class FoodHomePageController {
    private final FoodHomepageService foodHomepageService;

    @GetMapping("/all-latest")
    public ResponseEntity<ResponseObject> getLatestFoods() {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodHomepageService.getLatestProductFoods())
                        .message(EMessageResponse.GET_FOOD_HOMEPAGE.getMessage())
                        .build()
        );
    }

    @GetMapping("/all-deal-of-week")
    public ResponseEntity<ResponseObject> getDealOfWeekFoods() {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodHomepageService.getDealOfTheWeekFoods())
                        .message(EMessageResponse.GET_FOOD_HOMEPAGE.getMessage())
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addFoodHomepage(
            @RequestBody FoodHomepageDto foodHomepageDto
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodHomepageService.addFoodHomepage(foodHomepageDto))
                        .message(EMessageResponse.CREATE_FOOD_HOMEPAGE.getMessage())
                        .build()
        );
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteCategoryHomepage (
            @PathVariable Integer id,
            @RequestBody FoodHomepageDto foodHomepageDto
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(foodHomepageService.deleteFoodHomepage(id))
                        .message(EMessageResponse.DELETE_FOOD_HOMEPAGE.getMessage())
                        .build()
        );
    }
}
