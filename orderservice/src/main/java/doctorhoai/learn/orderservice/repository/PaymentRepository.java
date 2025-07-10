package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(
            value = """
            SELECT p FROM Payment p
            WHERE 
            (:isActive IS NULL OR p.isActive = :isActive) AND 
            (:startDate IS NULL OR p.createdAt >= :startDate) AND
            (:endDate IS NULL OR p.createdAt <= :endDate)
            """
    )
    Page<Payment> getPaymentListByFilter(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Boolean isActive,
            Pageable pageable
    );
}
