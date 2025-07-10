package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.ShippingFeeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingFeeConfigRepository extends JpaRepository<ShippingFeeConfig, Integer> {
}
