package doctorhoai.learn.foodservice.service.category;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.CategoryDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;

import java.util.List;


public interface CategoryService {

    List<CategoryDto> getCategoryByIds(List<Integer> ids);
    PageObject getAllCategories(Filter filter);
    CategoryDto addCategory(CategoryDto category);
    CategoryDto updateCategory(CategoryDto category, Integer id);
}
