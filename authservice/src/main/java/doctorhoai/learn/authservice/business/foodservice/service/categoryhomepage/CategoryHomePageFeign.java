package doctorhoai.learn.authservice.business.foodservice.service.categoryhomepage;

import doctorhoai.learn.authservice.business.foodservice.model.CategoryHomepageDto;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "foodservice",
        path = "/category-homepage",
        contextId = "categoryHomepageBusiness",
        fallbackFactory = CategoryHomePageFeignFallback.class,
        configuration = FeignConfig.class
)
public interface CategoryHomePageFeign {
    @GetMapping("/all")
    ResponseEntity<ResponseObject> getAllCategoryHomepage();

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addCategoryHomepage (
            @RequestBody CategoryHomepageDto categoryHomepageDto
    );

    @PutMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteCategoryHomepage (
            @PathVariable Integer id,
            @RequestBody CategoryHomepageDto categoryHomepageDto
    );
}
