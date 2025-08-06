package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.Ingredients;
import doctorhoai.learn.inventoryservice.model.IngredientsUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsUseRepository extends JpaRepository<IngredientsUse, Integer> {
    @Query(
            value = """
            SELECT iu FROM IngredientsUse iu 
            WHERE 
            (iu.orderId = :orderId) AND 
            (iu.isActive = :isActive)
            """
    )
    List<IngredientsUse> getIngredientsByOrderIdAndStatus(Integer orderId, boolean isActive);
}
