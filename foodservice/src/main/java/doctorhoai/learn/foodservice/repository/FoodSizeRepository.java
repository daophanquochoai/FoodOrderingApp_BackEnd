package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.Food;
import doctorhoai.learn.foodservice.model.FoodSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FoodSizeRepository extends JpaRepository<FoodSize, Integer> {
    Optional<FoodSize> getFoodSizeByIdAndIsActive(Integer id, Boolean isActive);


    @Query(
            value = """
            SELECT fs FROM FoodSize fs
            JOIN FETCH fs.food f
            JOIN FETCH fs.size s
            WHERE
            (:search IS NULL OR f.name like concat('%', :search, '%') OR f.desc like concat('%', :search, '%')) AND
            (:minDiscount IS NULL OR fs.discount >= :minDiscount) AND 
            (:maxDiscount IS NULL OR fs.discount <= :maxDiscount) AND 
            (:minPrice IS NULL OR fs.price >= :minPrice) AND 
            (:maxPrice IS NULL OR fs.price <= :maxPrice) AND
            (:minReady IS NULL OR fs.readyInMinutes >= :minReady) AND
            (:maxReady IS NULL OR fs.readyInMinutes <= :maxReady) AND 
            (:isActive IS NULL OR fs.isActive = :isActive) AND 
            (:startDate IS NULL OR fs.createdAt >= :startDate) AND
            (:endDate IS NULL OR fs.createdAt <= :endDate) AND
            (:foodIds IS NULL OR f.id in (:foodIds)) AND
            (:foodIds IS NULL OR s.id in (:foodIds))
            """
    )
    Page<FoodSize> getFoodSizeByFilter(
            String search,
            Float minDiscount,
            Float maxDiscount,
            Float minPrice,
            Float maxPrice,
            Float minReady,
            Float maxReady,
            Boolean isActive,
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<Integer> foodIds,
            List<Integer> sizeIds,
            Pageable pageable
    );

    @Query(
            value = """
            SELECT fs FROM FoodSize fs
            WHERE (:isActive IS NULL OR fs.isActive = :isActive) AND 
            (:ids IS NULL OR fs.id in (:ids))
            """
    )
    List<FoodSize> getFoodSizeByIdsAndIsActive(List<Integer> ids, Boolean isActive);

}
