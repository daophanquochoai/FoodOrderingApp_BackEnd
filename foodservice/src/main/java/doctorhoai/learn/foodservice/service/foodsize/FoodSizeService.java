package doctorhoai.learn.foodservice.service.foodsize;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.FoodSizeDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;

public interface FoodSizeService {

    FoodSizeDto createNewFoodSize(FoodSizeDto foodSizeDto);
    FoodSizeDto updateFoodSize(FoodSizeDto foodSizeDto, Integer id);
    PageObject getAllFoodSize(Filter filter);
}
