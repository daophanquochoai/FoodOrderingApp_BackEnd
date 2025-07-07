package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.IngredientError;
import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsErrorRepository extends JpaRepository<IngredientError, Integer> {

    @Query(
            value = """
            SELECT ie FROM IngredientError ie WHERE 
            (:isActive IS NULL OR :isActive = ie.isActive) AND 
            (:units IS NULL OR ie.unit in :units) AND 
            (:minQuantity IS NULL OR ie.quantity >= :minQuantity) AND
            (:maxQuantity IS NULL OR ie.quantity <= :maxQuantity) AND 
            (:search IS NULL OR (ie.reason LIKE concat('%',:search,'%')) OR (ie.reason LIKE concat('%', :search, '%') ))
            """
    )
    Page<IngredientError> getIngredientsErrorByFilter(
            Boolean isActive,
            String search,
            Integer minQuantity,
            Integer maxQuantity,
            List<EUnitType> units,
            Pageable pageable
    );
}
