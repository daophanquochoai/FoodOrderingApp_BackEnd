package doctorhoai.learn.foodservice.service.food;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;

import java.util.List;

public interface FoodService {
    List<FoodDto> getFoodByIds( List<Integer> ids);
    PageObject getFoodByFilter(Filter filter);
    FoodDto createFood(FoodDto food);
    FoodDto updateFood(FoodDto food, Integer id);
    FoodDto getFoodById(Integer id);
    void checkFood(List<Integer> ids);
    List<FoodDto> getAllIdsFood(List<Integer> ids);
    List<FoodDto> getAll();
}
