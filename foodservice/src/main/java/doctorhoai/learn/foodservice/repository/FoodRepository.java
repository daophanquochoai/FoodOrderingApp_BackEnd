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
            SELECT food FROM Food food WHERE
            (:ids IS NULL OR food.id IN :ids) AND 
            (:status IS NULL OR food.status IN :status) AND
            (:search IS NULL OR food.name LIKE CONCAT('%',:search,'%') OR food.desc LIKE CONCAT('%',:search,'%') ) 
            """
    )
    List<Food> getListFood(
            List<Integer> ids,
            List<EStatusFood> status,
            String search,
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
            (:ids IS NULL OR food.id IN :ids) 
            """
    )
    List<Food> checkFood(List<Integer> ids);
}
