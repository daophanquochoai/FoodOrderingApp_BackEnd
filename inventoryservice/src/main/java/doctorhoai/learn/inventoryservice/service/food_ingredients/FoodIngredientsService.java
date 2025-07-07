package doctorhoai.learn.inventoryservice.service.food_ingredients;

import doctorhoai.learn.inventoryservice.dto.FoodIngredientsDto;

import java.util.List;

public interface FoodIngredientsService {

    List<FoodIngredientsDto> getFoodIngredientsDtoByFoodId(Integer foodId);
    void createFoodIngredients(List<FoodIngredientsDto> foodIngredientsDtos, Integer id);
    void updateFoodIngredients(List<FoodIngredientsDto> foodIngredientsDtos, Integer foodId);
}
