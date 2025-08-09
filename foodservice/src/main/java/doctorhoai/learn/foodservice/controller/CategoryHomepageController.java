package doctorhoai.learn.foodservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.CategoryHomepageDto;
import doctorhoai.learn.foodservice.service.categoryhomepage.CategoryHomepageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category-homepage")
@RequiredArgsConstructor
public class CategoryHomepageController {
    private final CategoryHomepageService categoryHomepageService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllCategoryHomepage() {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(categoryHomepageService.getAllCategoryHomepage())
                        .message(EMessageResponse.GET_CATEGORY_HOMEPAGE.getMessage())
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addCategoryHomepage (
            @RequestBody CategoryHomepageDto categoryHomepageDto
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(categoryHomepageService.addCategoryHomepage(categoryHomepageDto))
                        .message(EMessageResponse.CREATE_CATEGORY_HOMEPAGE.getMessage())
                        .build()
        );
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteCategoryHomepage (
            @PathVariable Integer id,
            @RequestBody CategoryHomepageDto categoryHomepageDto
    ) {
        return ResponseEntity.ok(
            ResponseObject.builder()
                    .data(categoryHomepageService.deleteCategoryHomepage(id))
                    .message(EMessageResponse.DELETE_CATEGORY_HOMEPAGE.getMessage())
                    .build()
        );
    }
}