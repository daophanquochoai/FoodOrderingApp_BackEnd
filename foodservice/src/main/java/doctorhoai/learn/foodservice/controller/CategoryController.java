package doctorhoai.learn.foodservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.CategoryDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addCategory(
            @RequestBody @Valid CategoryDto categoryDto
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(categoryService.addCategory(categoryDto))
                        .message(EMessageResponse.CREATE_CATEGORY.getMessage())
                        .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getCategoryByFilter(
            @RequestBody Filter filter
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(categoryService.getAllCategories(filter))
                        .message(EMessageResponse.GET_CATEGORY.getMessage())
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> getCategoryByFilter(
            @RequestBody @Valid CategoryDto categoryDto,
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(categoryService.updateCategory(categoryDto, id))
                        .message(EMessageResponse.CREATE_CATEGORY.getMessage())
                        .build()
        );
    }
}
