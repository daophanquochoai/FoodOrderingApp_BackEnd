package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.Order;
import doctorhoai.learn.orderservice.model.enums.EStatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findOrderByIdAndStatus(Integer id, EStatusOrder status);
}
