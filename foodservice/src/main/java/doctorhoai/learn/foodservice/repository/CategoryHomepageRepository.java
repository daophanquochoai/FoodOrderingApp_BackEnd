package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.CategoryHomepage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryHomepageRepository extends JpaRepository<CategoryHomepage, Integer> {
    List<CategoryHomepage> findByIsActiveTrue();
    Optional<CategoryHomepage> findByCategory_Id(Integer categoryId);
}
