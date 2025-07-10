package doctorhoai.learn.foodservice.service.food;

import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.dto.FoodSizeDto;
import doctorhoai.learn.foodservice.dto.SizeDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.exception.CategoryNotFoundException;
import doctorhoai.learn.foodservice.exception.FoodNotFoundException;
import doctorhoai.learn.foodservice.model.Category;
import doctorhoai.learn.foodservice.model.Food;
import doctorhoai.learn.foodservice.model.FoodSize;
import doctorhoai.learn.foodservice.model.enums.EStatusFood;
import doctorhoai.learn.foodservice.repository.CategoryRepository;
import doctorhoai.learn.foodservice.repository.FoodRepository;
import doctorhoai.learn.foodservice.repository.FoodSizeRepository;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;
    private final FoodSizeRepository foodSizeRepository;


    @Override
    public List<FoodDto> getFoodByFilter(Filter filter) {

        Pageable pageable ;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }
        List<Food> foods = foodRepository.getListFood(
            filter.getId(),
            filter.getStatusFoods(),
                filter.getSearch(),
                pageable
        );
        if( filter.getDeep() == 0 ){
            return foods.stream().map(mapper::covertToFoodDto).toList();
        }else {
            return foods.stream().map( f -> {
                    FoodDto foodDto = mapper.covertToFoodDto(f);
                    if( f.getCategory() != null){
                        foodDto.setCategory(mapper.covertToCategoryDto(f.getCategory()));
                    }
                    if( f.getFoodSizes() != null && !f.getFoodSizes().isEmpty()){
                        foodDto.setFoodSizes(
                                f.getFoodSizes().stream().map( fs -> {
                                    FoodSizeDto foodSizeDto = mapper.convertToFoodSizeDto(fs);
                                    if( fs.getSize() != null){
                                        foodSizeDto.setSizeId(mapper.convertToSizeDto(fs.getSize()));
                                    }
                                    return foodSizeDto;
                                }).toList()
                        );
                    }
                    return foodDto;
                }
            ).toList();
        }
    }

    @Override
    @Transactional
    public FoodDto createFood(FoodDto fooddto) {
        Food food = mapper.convertToFood(fooddto);
        if( fooddto.getCategory() != null){
            Category category = categoryRepository.findById(fooddto.getCategory().getId()).orElseThrow(CategoryNotFoundException::new);
            food.setCategory(category);
        }
        food = foodRepository.save(food);
        return mapper.covertToFoodDto(food);
    }

    @Override
    public FoodDto updateFood(FoodDto foodDto, Integer id) {
        Food food = foodRepository.findById(id).orElseThrow(FoodNotFoundException::new);
        food.setName(foodDto.getName());
        food.setDesc(foodDto.getDesc());
        food.setImage(foodDto.getImage());
        food.setStatus(foodDto.getStatus());
        food = foodRepository.save(food);
        return mapper.covertToFoodDto(food);
    }

    @Override
    public FoodDto getFoodById(Integer id) {
        Food food = foodRepository.getFoodById(id).orElseThrow(FoodNotFoundException::new);
        if( food.getStatus() == EStatusFood.DELETE) {
            throw new FoodNotFoundException();
        }
        return mapper.covertToFoodDto(food);
    }

    @Override
    public void checkFood(List<Integer> ids) {
        List<Food> foods = foodRepository.checkFood(ids, null);
        if( ids.size() != foods.size()){
            throw new FoodNotFoundException();
        }
    }

    @Override
    public List<FoodDto> getAllIdsFood(List<Integer> ids) {
        List<Food> foods = foodRepository.checkFood(ids, EStatusFood.ACTIVE);
        return foods.stream().map(mapper::covertToFoodDto).toList();
    }


    private List<FoodSizeDto> convertListFoodSize(List<FoodSize> foodSizes){
        List<FoodSizeDto> foodSizeDtos = new ArrayList<>();
        for( FoodSize fs : foodSizes){
            FoodSizeDto foodSizeDto = mapper.convertToFoodSizeDto(fs);
            if( fs.getSize() != null && fs.getSize().getId() != null){
                SizeDto sizeDto = mapper.convertToSizeDto(fs.getSize());
                foodSizeDto.setSizeId(sizeDto);
            }
            foodSizeDtos.add(foodSizeDto);
        }
        return foodSizeDtos;
    }

}
