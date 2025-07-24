package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.FoodIngredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodIngredientsRepository extends JpaRepository<FoodIngredients, Integer> {

    List<FoodIngredients> getFoodIngredientsByFoodId(@Param("foodId") Integer foodId);


    @Query(
            value = """
            select f from FoodIngredients f
            where 
            (:isActive is null OR f.isActive in (:isActive)) AND 
            (:foodIds is null or f.foodId in (:foodIds))
            """
    )
    List<FoodIngredients> getFoodIngredientOfFood(
            List<Integer> foodIds,
            List<Boolean> isActive
    );

}
