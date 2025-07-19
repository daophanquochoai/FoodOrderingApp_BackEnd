package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(
            value = """
            SELECT c FROM Cart c
            WHERE c.userId = :userId AND 
            c.isActive = TRUE
            """
    )
    Optional<Cart> getCartByUserId(Integer userId);

    @Query(
            value = """
            SELECT c FROM Cart c
            WHERE 
            c.userId = :userId AND
            (:startDate IS NULL OR c.createdAt >= :startDate ) AND 
            (:endDate IS NULL OR c.createdAt <= :endDate ) AND 
            c.isActive = TRUE
            """
    )
    Optional<Cart> getCartByFilter(
            Integer userId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
