package doctorhoai.learn.foodservice.service.categoryhomepage;

import doctorhoai.learn.foodservice.dto.CategoryHomepageDto;

import java.util.List;

public interface CategoryHomepageService {
    List<CategoryHomepageDto> getAllCategoryHomepage();
    CategoryHomepageDto addCategoryHomepage(CategoryHomepageDto categoryHomepageDto);
    CategoryHomepageDto deleteCategoryHomepage(Integer id);
}
