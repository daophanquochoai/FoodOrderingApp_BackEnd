package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.FoodHomepage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodHomepageRepository extends JpaRepository<FoodHomepage, Integer> {
    List<FoodHomepage> findByIsActiveTrueAndFeature(String feature);
    Optional<FoodHomepage> findByFeatureAndFood_Id(String feature, Integer foodId);
}
