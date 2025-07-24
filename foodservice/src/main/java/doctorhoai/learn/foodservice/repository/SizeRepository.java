package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

    @Query(
        value = """
        SELECT s FROM Size s WHERE 
        (:search IS NULL OR s.name LIKE CONCAT('%',:search, '%') ) AND 
        (:isActive IS NULL OR s.isActive = :isActive) AND 
        (:startDate IS NULL OR s.createdAt >= :startDate) AND
        (:endDate IS NULL OR s.createdAt <= :endDate) 
        """
    )
    Page<Size> getSizeByFilter(
            String search,
            Boolean isActive,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    Optional<Size> getSizeByIdAndIsActive(Integer id, Boolean isActive);

    Optional<Size> getSizeById(Integer id);

    Optional<Size> getSizeByNameAndIsActive(String name, Boolean isActive);

    @Query(
            value = """
            SELECT s FROM Size s WHERE
            s.isActive = true AND
            (:ids IS NULL OR s.id IN (:ids))
            """
    )
    List<Size> getSizeByIds(List<Integer> ids);

    @Query(
            value = """
            SELECT s FROM Size s WHERE
            (:state is true OR s.isActive = true)
            """
    )
    List<Size> getSize(Boolean state);
}
