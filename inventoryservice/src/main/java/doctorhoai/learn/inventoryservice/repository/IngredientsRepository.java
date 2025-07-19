package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.FoodIngredients;
import doctorhoai.learn.inventoryservice.model.Ingredients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Integer> {

    @Query(
            value = """
            select i from Ingredients i where 
            (:ids is null or i.id in :ids) and 
            (i.isActive = true)
            """
    )
    List<Ingredients> findIngredientsByIds(List<Integer> ids);

    @Query(
            value = """
            SELECT i FROM Ingredients i WHERE
            (:search IS NULL OR i.name LIKE CONCAT('%', :search, '%')) AND 
            (:unit IS NULL OR i.unit IN :unit) AND 
            (:minThreshold IS NULL OR i.lowThreshold >= :minThreshold) AND
            (:maxThreshold IS NULL OR i.lowThreshold <= :maxThreshold) AND
            (:startDate IS NULL OR i.createdAt >= :startDate) AND
            (:endDate IS NULL OR i.createdAt <= :endDate) AND 
            (:isActive IS NULL OR i.isActive = :isActive)
            """
    )
    Page<Ingredients> findIngredientsByFilter(
            String search,
            List<String> unit,
            Integer minThreshold,
            Integer maxThreshold,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Boolean isActive,
            Pageable pageable
    );

    @Query(
            value = """
            SELECT i FROM FoodIngredients fi
           JOIN fi.ingredients i
            WHERE
            (i IS NOT NULL AND (:ids IS NULL OR i.id in :ids)) AND 
            i.isActive = TRUE
            """
    )
    List<Ingredients> getFoodIngredientsByIds(@Param("ids") List<Integer> ids);
}
