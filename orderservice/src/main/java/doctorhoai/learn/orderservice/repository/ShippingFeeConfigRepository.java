package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.ShippingFeeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingFeeConfigRepository extends JpaRepository<ShippingFeeConfig, Integer> {

    @Query(
            value = """
            SELECT s FROM ShippingFeeConfig s WHERE 
            s.isActive = true
            """
    )
    List<ShippingFeeConfig> findShippingFeeConfigActive();

    @Query(
            value = """
            SELECT s FROM ShippingFeeConfig s WHERE
            s.isActive = true
            order by s.createdAt desc
            limit 1
            """
    )
    Optional<ShippingFeeConfig> findShippingFeeConfigByNew();
}
