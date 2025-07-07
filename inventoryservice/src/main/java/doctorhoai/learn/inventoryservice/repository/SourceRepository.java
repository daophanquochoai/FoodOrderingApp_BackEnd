package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.Source;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SourceRepository extends JpaRepository<Source, Integer> {

    @Query(
            value = """
            select s from Source s where 
            (
                :search is null 
                or (s.name like concat('%', :search, '%')) 
                or (s.address like concat('%', :search, '%'))
                or (s.phoneNumber like concat('%', :search, '%'))
                or (s.email like concat('%', :search, '%'))
                or (s.taxCode like concat('%', :search, '%')) 
            ) and
            (:isActive is null or s.isActive = :isActive) and 
            (:startDate is null or s.createdAt >= :startDate) and
            (:endDate is null or s.createdAt <= :endDate)
            """
    )
    List<Source> getAllSources(
            String search,
            Boolean isActive,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
}
