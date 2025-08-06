package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.Food;
import doctorhoai.learn.foodservice.model.enums.EStatusFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    @Query(
            value = """
            SELECT f from Food f 
            WHERE 
            (:status IS NULL OR :status = f.status) AND 
            (:ids IS NULL OR f.id IN (:ids))
            """
    )
    List<Food> getFoodByIds(List<Integer> ids, EStatusFood status);

    @EntityGraph(attributePaths = {
            "foodSizes",
            "foodSizes.size"
    })
    @Query("""
    SELECT food FROM Food food
    WHERE
        (:ids IS NULL OR food.id IN :ids) AND 
        (:status IS NULL OR food.status IN :status) AND
        (:categoryId IS NULL OR food.category.id IN :categoryId) AND
        (:search IS NULL OR food.name LIKE CONCAT('%',:search,'%') OR food.desc LIKE CONCAT('%',:search,'%')) AND 
        (
            :minDiscount IS NULL OR EXISTS (
                SELECT 1 FROM food.foodSizes fs WHERE COALESCE(fs.discount, -1000) >= :minDiscount
            )
        ) AND
        (
            :maxDiscount IS NULL OR EXISTS (
                SELECT 1 FROM food.foodSizes fs WHERE COALESCE(fs.discount, 1000) <= :maxDiscount
            )
        ) AND
        (
            :minPrice IS NULL OR EXISTS (
                SELECT 1 FROM food.foodSizes fs WHERE COALESCE(fs.price, -1) >= :minPrice
            )
        ) AND
        (
            :maxPrice IS NULL OR EXISTS (
                SELECT 1 FROM food.foodSizes fs WHERE COALESCE(fs.price, 10000000000) <= :maxPrice
            )
        ) AND
        (
            :minReady IS NULL OR EXISTS (
                SELECT 1 FROM food.foodSizes fs WHERE COALESCE(fs.readyInMinutes, -1) >= :minReady
            )
        ) AND
        (
            :maxReady IS NULL OR EXISTS (
                SELECT 1 FROM food.foodSizes fs WHERE COALESCE(fs.readyInMinutes, -1) <= :maxReady
            )
        ) AND
        (
            :sizeIds IS NULL OR EXISTS (
                SELECT 1 FROM food.foodSizes fs WHERE fs.size.id IN :sizeIds
            )
        )
""")
    Page<Food> getListFood(
            @Param("ids") List<Integer> ids,
            @Param("status") List<EStatusFood> status,
            @Param("search") String search,
            @Param("minDiscount") Float minDiscount,
            @Param("maxDiscount") Float maxDiscount,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("minReady") Float minReady,
            @Param("maxReady") Float maxReady,
            @Param("sizeIds") List<Integer> sizeIds,
            @Param("categoryId") List<Integer> categoryId,
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
            food.id = :id 
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

    @Query(
            value = """
            SELECT food FROM Food food WHERE
            (:state is true OR food.status = 'ACTIVE')
            """
    )
    List<Food> getFood(Boolean state);

}
