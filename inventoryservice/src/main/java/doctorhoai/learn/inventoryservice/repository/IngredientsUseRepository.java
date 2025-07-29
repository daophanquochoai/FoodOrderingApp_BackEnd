package doctorhoai.learn.inventoryservice.repository;

import doctorhoai.learn.inventoryservice.model.IngredientsUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientsUseRepository extends JpaRepository<IngredientsUse, Integer> {
}
