package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.Food;
import doctorhoai.learn.foodservice.model.enums.EStatusFood;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    @Query(
            value = """
            SELECT food FROM Food food
            JOIN FETCH food.foodSizes fs 
             JOIN FETCH fs.size s
            WHERE
            (:ids IS NULL OR food.id IN :ids) AND 
            (:status IS NULL OR food.status IN :status) AND
            (:search IS NULL OR food.name LIKE CONCAT('%',:search,'%') OR food.desc LIKE CONCAT('%',:search,'%') ) AND 
            (:minDiscount IS NULL OR fs.discount >= :minDiscount) AND
            (:maxDiscount IS NULL OR fs.discount <= :maxDiscount) AND
            (:minPrice IS NULL OR fs.price >= :minPrice) AND
            (:maxPrice IS NULL OR fs.price <= :maxPrice) AND
            (:minReady IS NULL OR fs.readyInMinutes >= :minReady) AND
            (:maxReady IS NULL OR fs.readyInMinutes <= :maxReady) AND
            (:sizeIds IS NULL OR s.id in (:sizeIds))
            """
    )
    List<Food> getListFood(
            List<Integer> ids,
            List<EStatusFood> status,
            String search,
            Float minDiscount,
            Float maxDiscount,
            Float minPrice,
            Float maxPrice,
            Float minReady,
            Float maxReady,
            List<Integer> sizeIds,
            Pageable pageable
    );

    @Query(
            value = """
            SELECT food FROM Food food WHERE
            (:ids IS NULL OR food.id IN :ids) AND
            (food.status = 'ACTIVE')
            """
    )
    List<Food> getListFoodByListId(
            List<Integer> ids
    );

    @Query(
            value = """
            SELECT food FROM Food food WHERE
            food.id IN :id and food.status = 'ACTIVE'
            """
    )
    Optional<Food> getFoodById(Integer id);

    @Query(
            value = """
            SELECT food FROM Food food WHERE
            (:ids IS NULL OR food.id IN :ids) AND 
            (:status IS NULL OR food.status = :status)
            """
    )
    List<Food> checkFood(List<Integer> ids, EStatusFood status);

    @Query(
            value = """
            SELECT food FROM Food food WHERE
            (food.status = :status)
            """
    )
    List<Food> getAllFood(EStatusFood status);

}
