package doctorhoai.learn.authservice.business.foodservice.controller;

import doctorhoai.learn.authservice.business.foodservice.model.CategoryDto;
import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.service.categoryservice.CategoryFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryFeign categoryFeign;

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addCategory(
            @RequestBody @Valid CategoryDto categoryDto
    ){
        return categoryFeign.addCategory(categoryDto);
    }

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getCategoryByFilter(
            @RequestBody Filter filter
    ){
        return categoryFeign.getCategoryByFilter(filter);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> getCategoryByFilter(
            @RequestBody @Valid CategoryDto categoryDto,
            @PathVariable Integer id
    ){
        return categoryFeign.getCategoryByFilter(categoryDto, id);
    }
}
