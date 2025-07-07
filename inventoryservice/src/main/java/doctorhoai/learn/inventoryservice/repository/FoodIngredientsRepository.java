package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.FoodIngredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodIngredientsRepository extends JpaRepository<FoodIngredients, Integer> {

    @Query(
            value = """
            SELECT  fi FROM FoodIngredients fi
            WHERE 
            fi.foodId = :foodId AND 
            fi.isActive is true
            """
    )
    List<FoodIngredients> getFoodIngredientsByFoodId(Integer foodId);

}
