package doctorhoai.learn.foodservice.service.categoryhomepage;

import doctorhoai.learn.foodservice.dto.CategoryHomepageDto;
import doctorhoai.learn.foodservice.exception.CategoryHomepageNotFoundException;
import doctorhoai.learn.foodservice.exception.CategoryNotFoundException;
import doctorhoai.learn.foodservice.model.Category;
import doctorhoai.learn.foodservice.model.CategoryHomepage;
import doctorhoai.learn.foodservice.repository.CategoryHomepageRepository;
import doctorhoai.learn.foodservice.repository.CategoryRepository;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryHomepageServiceImpl implements CategoryHomepageService {
    private final CategoryHomepageRepository categoryHomepageRepository;
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    @Override
    public List<CategoryHomepageDto> getAllCategoryHomepage() {
        List<CategoryHomepage> homepageList = categoryHomepageRepository.findByIsActiveTrue();
        return homepageList.stream()
                .map(mapper::covertToCategoryHomepageDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryHomepageDto addCategoryHomepage(CategoryHomepageDto categoryHomepageDto) {
        if (categoryHomepageDto.getCategory() == null || categoryHomepageDto.getCategory().getId() == null) {
            throw new IllegalArgumentException("Category id must not be null");
        }

        Integer categoryId = categoryHomepageDto.getCategory().getId();

        Optional<CategoryHomepage> homepageOpt = categoryHomepageRepository.findByCategory_Id(categoryId);

        CategoryHomepage categoryHomepage;

        if (homepageOpt.isPresent()) {
            categoryHomepage = homepageOpt.get();
            categoryHomepage.setIsActive(true);
        } else {
            categoryHomepage = mapper.covertToCategoryHomepage(categoryHomepageDto);
            Category category = categoryRepository.findById(categoryHomepageDto.getCategory().getId()).orElseThrow(CategoryNotFoundException::new);
            categoryHomepage.setCategory(category);
            categoryHomepage.setIsActive(true);
        }
        categoryHomepage = categoryHomepageRepository.save(categoryHomepage);
        return mapper.covertToCategoryHomepageDto(categoryHomepage);
    }

    @Override
    public CategoryHomepageDto deleteCategoryHomepage(Integer id) {
        CategoryHomepage categoryHomepage = categoryHomepageRepository.findById(id)
                .orElseThrow(CategoryHomepageNotFoundException::new);
        categoryHomepage.setIsActive(false);
        categoryHomepage = categoryHomepageRepository.save(categoryHomepage);
        return mapper.covertToCategoryHomepageDto(categoryHomepage);
    }
}
