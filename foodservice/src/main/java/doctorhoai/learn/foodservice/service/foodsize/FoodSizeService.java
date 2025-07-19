package doctorhoai.learn.foodservice.service.foodsize;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.FoodSizeDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;

import java.util.List;

public interface FoodSizeService {

    FoodSizeDto createNewFoodSize(FoodSizeDto foodSizeDto);
    FoodSizeDto updateFoodSize(FoodSizeDto foodSizeDto, Integer id);
    PageObject getAllFoodSize(Filter filter);
    FoodSizeDto getFoodSizeById(Integer id);
    List<FoodSizeDto> getFoodSizeList(List<Integer> idsFood);
}
