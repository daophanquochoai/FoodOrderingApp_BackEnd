package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query(
            value = """
            SELECT oi FROM OrderItem oi 
            WHERE 
            (:idsOrder IS NULL OR oi.orderId.id IN (:idsOrder)) AND 
            (oi.isActive IS TRUE)
            """
    )
    List<OrderItem> findByOrder(List<Integer> idsOrder);
}
