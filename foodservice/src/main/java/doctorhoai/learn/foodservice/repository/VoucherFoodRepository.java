package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.VoucherFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherFoodRepository extends JpaRepository<VoucherFood, Integer> {
    @Query(
            value = """
            SELECT vf from VoucherFood vf
            WHERE 
            (:foodIds IS NULL OR vf.food.id IN (:foodIds))
            """
    )
    List<VoucherFood> getVoucherFoodByFoodIds( Integer[] foodIds);
}
