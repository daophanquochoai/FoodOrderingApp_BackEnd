package doctorhoai.learn.aiservice.service.semantic;

import doctorhoai.learn.aiservice.dto.FoodDto;

import java.util.List;

public interface SearchFoodService {

    void addFood(FoodDto dto);
    void removeFood(FoodDto dto);
    void updateFood(FoodDto dto);
    List<Integer> searchFood(String search);
}
