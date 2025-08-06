package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.Order;
import doctorhoai.learn.orderservice.model.enums.EStatusOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findOrderByIdAndStatus(Integer id, EStatusOrder status);

    @Query(
            value = """
            SELECT o FROM Order o
            WHERE 
            (:id IS NULL OR o.userId IN :id) AND
            (:search IS NULL 
                OR o.name LIKE CONCAT('%',:search,'%') 
                OR o.address LIKE CONCAT('%',:search,'%') 
                OR o.phoneNumber LIKE CONCAT('%',:search,'%')
            ) AND 
            (:start IS NULL OR o.createdAt >= :start) AND 
            (:end IS NULL OR o.createdAt <= :end) AND 
            (:statusOrders IS NULL OR o.status IN (:statusOrders)) AND 
            (:type IS NULL OR ( :type is false AND o.tableNumber IS NOT NULL ) OR (:type IS TRUE AND o.tableNumber IS NULL) ) AND 
            (:shipperId IS NULL OR o.shipperId = :shipperId)
            """
    )
    Page<Order> getOrderByFilter(
            List<Integer> id,
            String search,
            LocalDateTime start,
            LocalDateTime end,
            List<EStatusOrder> statusOrders,
            Boolean type,
            Integer shipperId,
            Pageable pageable
    );
}
