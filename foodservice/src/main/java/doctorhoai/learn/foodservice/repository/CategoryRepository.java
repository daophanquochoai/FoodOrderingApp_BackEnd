package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.Category;
import doctorhoai.learn.foodservice.model.enums.EStatusCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(
            value = """
            SELECT c FROM Category c WHERE 
            (:ids IS NULL OR c.id IN :ids) AND 
            (:status IS NULL OR c.status IN :status) AND 
            (:search IS NULL OR c.name like CONCAT('%', :search, '%'))
            """
    )
    List<Category> getAllCategories(
            List<Integer> ids,
            List<EStatusCategory> status,
            String search,
            Pageable pageable
    );

    @Query(
            value = """
            SELECT c FROM Category c WHERE  
            (:ids IS NULL OR c.id IN :ids) AND
            (c.status = 'ACTIVE')
            """
    )
    List<Category> getListCategoryByListId(
            List<Integer> ids
    );

}
