package doctorhoai.learn.authservice.business.foodservice.service.foodsizeservice;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.FoodSizeDto;
import doctorhoai.learn.authservice.business.userservice.service.config.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
        name = "foodservice",
        path = "food_size",
        contextId = "foodSizeFeignBusiness",
        fallbackFactory = FoodSizeFeignCFallback.class,
        configuration = FeignConfig.class
)
public interface FoodSizeFeign {
    @PostMapping("/add")
    ResponseEntity<ResponseObject> createNewFoodSize(
            @RequestBody @Valid FoodSizeDto foodSizeDto
    );

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllFoodSize(
            @RequestBody Filter filter
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateFoodSize(
            @RequestBody @Valid FoodSizeDto foodSizeDto,
            @PathVariable Integer id
    );
}
