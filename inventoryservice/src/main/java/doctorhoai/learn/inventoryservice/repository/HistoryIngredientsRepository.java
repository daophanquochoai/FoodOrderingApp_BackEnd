package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.HistoryIngredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryIngredientsRepository extends JpaRepository<HistoryIngredients, Integer> {
}
