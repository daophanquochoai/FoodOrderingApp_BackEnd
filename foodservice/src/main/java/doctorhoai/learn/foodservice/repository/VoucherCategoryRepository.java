package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.VoucherCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherCategoryRepository extends JpaRepository<VoucherCategory, Integer> {

    @Query(
            value = """
            SELECT vc FROM VoucherCategory vc
            WHERE 
            (:categoryIds IS NULL OR vc.category.id IN :categoryIds)
            """
    )
    List<VoucherCategory> getVoucherCategoriesByCategoryIds(Integer[] categoryIds);
}
