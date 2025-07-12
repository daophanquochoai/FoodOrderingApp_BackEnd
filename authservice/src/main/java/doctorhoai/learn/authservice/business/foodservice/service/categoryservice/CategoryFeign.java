package doctorhoai.learn.authservice.business.foodservice.service.categoryservice;

import doctorhoai.learn.authservice.business.foodservice.model.CategoryDto;
import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.userservice.service.config.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "foodservice",
        path = "/food",
        contextId = "foodFeignBusiness",
        fallbackFactory = CategoryFeignFallback.class,
        configuration = FeignConfig.class
)
public interface CategoryFeign {
    @PostMapping("/add")
    ResponseEntity<ResponseObject> addCategory(
            @RequestBody @Valid CategoryDto categoryDto
    );

    @GetMapping("/all")
    ResponseEntity<ResponseObject> getCategoryByFilter(
            @RequestBody Filter filter
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> getCategoryByFilter(
            @RequestBody @Valid CategoryDto categoryDto,
            @PathVariable Integer id
    );
}
