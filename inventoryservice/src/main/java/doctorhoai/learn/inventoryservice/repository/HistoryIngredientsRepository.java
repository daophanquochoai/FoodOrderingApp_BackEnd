package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.HistoryIngredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryIngredientsRepository extends JpaRepository<HistoryIngredients, Integer> {


    @Query(
            value = """
            SELECT hi FROM HistoryIngredients hi
            WHERE 
            (hi.isActive = true) AND 
            (hi.quantity > hi.usedUnit) AND 
            (:ingredientIds IS NULL OR hi.ingredientsId.id IN :ingredientIds)
            order by hi.createdAt desc 
            """
    )
    List<HistoryIngredients> getIngredientsInInventory(
        List<Integer> ingredientIds
    );

    List<HistoryIngredients> findByHistoryId(Integer historyId);
}
