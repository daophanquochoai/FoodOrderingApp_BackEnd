package doctorhoai.learn.orderservice.repository;

import doctorhoai.learn.orderservice.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
}
