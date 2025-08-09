package doctorhoai.learn.authservice.business.foodservice.controller;

import doctorhoai.learn.authservice.business.foodservice.model.CategoryHomepageDto;
import doctorhoai.learn.authservice.business.foodservice.service.categoryhomepage.CategoryHomePageFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category-homepage")
@RequiredArgsConstructor
public class CategoryHomePageController {

    private final CategoryHomePageFeign categoryHomePageFeign;

    @GetMapping("/all")
    ResponseEntity<ResponseObject> getAllCategoryHomepage(){
        return categoryHomePageFeign.getAllCategoryHomepage();
    }

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addCategoryHomepage (
            @RequestBody CategoryHomepageDto categoryHomepageDto
    ){
        return categoryHomePageFeign.addCategoryHomepage(categoryHomepageDto);
    }

    @PutMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteCategoryHomepage (
            @PathVariable Integer id,
            @RequestBody CategoryHomepageDto categoryHomepageDto
    ){
        return categoryHomePageFeign.deleteCategoryHomepage(id, categoryHomepageDto);
    }
}
