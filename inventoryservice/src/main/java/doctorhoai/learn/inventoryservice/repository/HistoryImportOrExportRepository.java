package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.HistoryImportOrExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryImportOrExportRepository extends JpaRepository<HistoryImportOrExport, Integer> {

    @EntityGraph(attributePaths = {"historyIngredients"})
    @Query("""
    SELECT h FROM HistoryImportOrExport h
    WHERE
        (:historyImportOrExportIds IS NULL OR h.id IN (:historyImportOrExportIds)) AND
        (h.sourceId IS NOT NULL AND (:sourceId IS NULL OR h.sourceId.id IN (:sourceId))) AND
        (:inventory IS NULL OR 
            EXISTS (
                SELECT 1 FROM HistoryIngredients hh 
                WHERE hh.history = h AND
                (
                    (:inventory = TRUE AND hh.usedUnit < hh.quantity) OR 
                    (:inventory = FALSE AND hh.usedUnit = hh.quantity)
                )
            )
        ) AND
        (:isActive IS NULL OR :isActive = h.isActive) AND
        (:ingredientsId IS NULL OR EXISTS (
            SELECT 1 FROM HistoryIngredients hh2 
            WHERE hh2.history = h AND hh2.ingredientsId.id IN (:ingredientsId)
        )) AND
        (:minPrice IS NULL OR EXISTS (
            SELECT 1 FROM HistoryIngredients hh3 
            WHERE hh3.history = h AND hh3.pricePerUnit >= :minPrice
        )) AND
        (:maxPrice IS NULL OR EXISTS (
            SELECT 1 FROM HistoryIngredients hh4 
            WHERE hh4.history = h AND hh4.pricePerUnit <= :maxPrice
        )) AND
        (:startDate IS NULL OR h.createdAt >= :startDate) AND
        (:endDate IS NULL OR h.createdAt <= :endDate) AND
        (
            :search IS NULL OR
            LOWER(h.bath_code) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(h.note) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
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

    @Query(
            value = """
            SELECT h FROM HistoryImportOrExport h 
            WHERE h.bath_code = :batchCode
            """
    )
    Optional<HistoryImportOrExport> getByBathCode(String batchCode);
}
