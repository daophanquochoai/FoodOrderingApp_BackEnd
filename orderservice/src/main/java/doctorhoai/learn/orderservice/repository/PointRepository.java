package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
    Optional<Point> findByUserIdAndIsActive(Integer id, boolean isActive);
}
