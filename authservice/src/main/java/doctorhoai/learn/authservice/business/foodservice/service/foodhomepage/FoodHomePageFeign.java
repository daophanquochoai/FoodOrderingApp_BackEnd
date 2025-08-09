package doctorhoai.learn.authservice.business.foodservice.service.foodhomepage;

import doctorhoai.learn.authservice.business.foodservice.model.FoodHomepageDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "foodservice",
        path = "/food-homepage",
        contextId = "foodHomePageBusiness",
        fallbackFactory = FoodHomePageFeignFallback.class,
        configuration = FeignConfig.class
)
public interface FoodHomePageFeign {
    @GetMapping("/all-latest")
    ResponseEntity<ResponseObject> getLatestFoods();

    @GetMapping("/all-deal-of-week")
    ResponseEntity<ResponseObject> getDealOfWeekFoods();

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addFoodHomepage(
            @RequestBody FoodHomepageDto foodHomepageDto
    );

    @PutMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteCategoryHomepage (
            @PathVariable Integer id,
            @RequestBody FoodHomepageDto foodHomepageDto
    );
}
