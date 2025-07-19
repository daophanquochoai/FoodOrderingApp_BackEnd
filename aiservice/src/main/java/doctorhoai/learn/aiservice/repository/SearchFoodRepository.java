package doctorhoai.learn.aiservice.repository;

import doctorhoai.learn.aiservice.model.SearchFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchFoodRepository extends JpaRepository<SearchFood, Integer> {

    Optional<SearchFood> findByFoodId(Integer foodId);

    @Query(
            value = """
        SELECT *, embedding <=> CAST(:embedding AS vector) AS distance
        FROM search_food
        WHERE embedding <=> CAST(:embedding AS vector) < :threshold
        ORDER BY distance
        LIMIT :limit
    """,
            nativeQuery = true
    )
    List<SearchFood> searchSimilarFoods(
            @Param("embedding") float[] embedding,
            @Param("threshold") float threshold,
            @Param("limit") int limit
    );
}
