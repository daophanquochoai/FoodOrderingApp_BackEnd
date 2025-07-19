package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.HistoryImportOrExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoryImportOrExportRepository extends JpaRepository<HistoryImportOrExport, Integer> {

    @Query(
            value = """
        SELECT h FROM HistoryImportOrExport h
        LEFT JOIN FETCH h.historyIngredients hh
        WHERE
        (:historyImportOrExportIds IS NULL OR h.id IN (:historyImportOrExportIds)) AND
        (h.sourceId is not null and (:sourceId IS NULL OR h.sourceId.id IN (:sourceId)) ) AND
        (:inventory IS NULL OR 
            (hh IS NOT NULL AND :inventory = TRUE AND hh.usedUnit < hh.quantity) OR 
            (hh IS NOT NULL AND :inventory = FALSE AND hh.usedUnit = hh.quantity)) AND
        (:isActive IS NULL OR :isActive = h.isActive) AND
        (:ingredientsId IS NULL OR (hh IS NOT NULL AND hh.ingredientsId IS NOT NULL AND hh.ingredientsId.id IN (:ingredientsId))) AND
        (:minPrice IS NULL OR (hh IS NOT NULL AND hh.pricePerUnit >= :minPrice)) AND
        (:maxPrice IS NULL OR (hh IS NOT NULL AND hh.pricePerUnit <= :maxPrice)) AND
        (:startDate IS NULL OR h.createdAt >= :startDate) AND
        (:endDate IS NULL OR h.createdAt <= :endDate) AND
        (
            :search IS NULL OR
            LOWER(h.bath_code) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(h.note) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """
    )
    Page<HistoryImportOrExport> getHistoryImportOrExportByFilter(
            @Param("historyImportOrExportIds") List<Integer> historyImportOrExportIds,
            @Param("sourceId") List<Integer> sourceId,
            @Param("inventory") Boolean inventory,
            @Param("isActive") Boolean isActive,
            @Param("ingredientsId") List<Integer> ingredientsId,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("search") String search,
            Pageable pageable
    );

}
