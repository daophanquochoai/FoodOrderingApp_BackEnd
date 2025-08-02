package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.Category;
import doctorhoai.learn.foodservice.model.enums.EStatusCategory;
import doctorhoai.learn.foodservice.model.enums.EStatusFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(
            value = """
            SELECT c FROM Category c
            WHERE
            (:ids IS NULL OR c.id IN (:ids)) AND 
            (:status IS NULL OR c.status = :status)
            """
    )
    List<Category> getCategoryByIds(List<Integer> ids, EStatusFood status);

    @Query(
            value = """
            SELECT c FROM Category c WHERE 
            (:ids IS NULL OR c.id IN :ids) AND 
            (:status IS NULL OR c.status IN :status) AND 
            (:search IS NULL OR c.name like CONCAT('%', :search, '%'))
            """
    )
    Page<Category> getAllCategories(
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

    Optional<Category> findByIdAndStatus(Integer integer, EStatusCategory statusCategory);

    @Query(
            value = """
            SELECT c FROM Category c WHERE
            (:state is true OR c.status = 'ACTIVE')
            """
    )
    List<Category> getCategory( Boolean state);

}
