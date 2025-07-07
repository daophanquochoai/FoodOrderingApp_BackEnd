package doctorhoai.learn.foodservice.service.category;

import doctorhoai.learn.foodservice.dto.CategoryDto;
import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.exception.CategoryNotFoundException;
import doctorhoai.learn.foodservice.model.Category;
import doctorhoai.learn.foodservice.repository.CategoryRepository;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper mapper;


    @Override
    public List<CategoryDto> getAllCategories(Filter filter) {
        Pageable pageable ;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize());
        }
        List<Category> categories = categoryRepository.getAllCategories(
                filter.getId(),
                filter.getStatusCategories(),
                filter.getSearch(),
                pageable
        );
        if( filter.getDeep() == 0 ){
            return categories.stream().map(mapper::covertToCategoryDto).toList();
        }else{
            return categories.stream().map( c-> {
                CategoryDto categoryDto = mapper.covertToCategoryDto(c);
                if( c.getFoods() != null && !c.getFoods().isEmpty()){
                    List<FoodDto> foods = c.getFoods().stream().map(mapper::covertToFoodDto).toList();
                    categoryDto.setFoods(foods);
                }
                return categoryDto;
            }).toList();
        }
    }

    @Override
    @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapper.covertToCategory(categoryDto);
        if( categoryDto.getParent() != null ){
            Category parent = categoryRepository.findById(categoryDto.getParent().getId()).orElseThrow(CategoryNotFoundException::new);
            category.setParentId(parent);
        }
        category = categoryRepository.save(category);
        return mapper.covertToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        category.setName(categoryDto.getName());
        category.setDesc(categoryDto.getDesc());
        category.setStatus(categoryDto.getStatus());
        category.setImage( categoryDto.getImage() );
        if( categoryDto.getParent() == null){
            category.setParentId(null);
        }else if(category.getParentId()  == null ||  categoryDto.getParent().getId() != category.getParentId().getId()){
            Category parent = categoryRepository.findById(categoryDto.getParent().getId()).orElseThrow(CategoryNotFoundException::new);
            category.setParentId(parent);
        }
        category = categoryRepository.save(category);
        return mapper.covertToCategoryDto(category);
    }
}
