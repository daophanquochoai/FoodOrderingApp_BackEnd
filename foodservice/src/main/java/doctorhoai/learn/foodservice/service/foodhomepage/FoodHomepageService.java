package doctorhoai.learn.foodservice.service.foodhomepage;

import doctorhoai.learn.foodservice.dto.FoodHomepageDto;

import java.util.List;

public interface FoodHomepageService {
    List<FoodHomepageDto> getLatestProductFoods();
    List<FoodHomepageDto> getDealOfTheWeekFoods();
    FoodHomepageDto addFoodHomepage(FoodHomepageDto foodHomepageDto);
    FoodHomepageDto deleteFoodHomepage(Integer id);
}
