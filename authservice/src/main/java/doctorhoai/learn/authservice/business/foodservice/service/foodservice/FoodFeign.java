package doctorhoai.learn.authservice.business.foodservice.service.foodservice;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.FoodDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "foodservice",
        path = "/food",
        contextId = "foodFeignBusinessService",
        fallbackFactory = FoodFeignFallback.class,
    configuration = FeignConfig.class
)
public interface FoodFeign {
    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllFood(
            @RequestBody Filter filter
    );
    @PostMapping("/ids")
    ResponseEntity<ResponseObject> getAllIdsFood(
            @RequestBody List<Integer> ids
    );

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addFood(
            @RequestBody @Valid FoodDto dto
    );
    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateFood(
            @PathVariable Integer id,
            @RequestBody @Valid FoodDto dto
    );

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getFood(@PathVariable Integer id) ;

    @PostMapping("/check_food")
    ResponseEntity<ResponseObject> checkFood(
            @RequestBody List<Integer> ids
    );
}
