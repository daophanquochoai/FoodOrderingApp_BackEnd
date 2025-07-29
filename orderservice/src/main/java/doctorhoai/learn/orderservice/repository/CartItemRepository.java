package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {


    @Query(
            value = """
            SELECT ci FROM CartItem ci WHERE ci.cartId.userId = :id AND 
            (:startDate IS NULL OR ci.createdAt >= :startDate) AND 
            (:endDate IS NULL OR ci.createdAt <= :endDate) AND 
            ci.isActive = TRUE ORDER BY ci.createdAt DESC   
            """
    )
    Page<CartItem> getCartItemByFilter(
            Integer id,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    @Query(
            value = """
            SELECT ci FROM CartItem ci WHERE
            (:ids IS NULL OR ci.cartId.id IN (:ids)) AND ci.isActive = TRUE 
            """
    )
    List<CartItem> getCartItemByIds( List<Integer> ids );

    @Query(
            value = """
            SELECT ci FROM CartItem ci WHERE
            (ci.cartId.userId = :userId) AND 
            (ci.isActive = true)
            """
    )
    List<CartItem> getCartItemByCartToUpdate( Integer userId);
}

