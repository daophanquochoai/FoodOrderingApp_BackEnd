package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.HistoryPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryPointRepository extends JpaRepository<HistoryPoint, Integer> {
}
