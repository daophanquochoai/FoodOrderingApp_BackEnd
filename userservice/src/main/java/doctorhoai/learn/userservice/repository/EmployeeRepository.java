package doctorhoai.learn.userservice.repository;

import doctorhoai.learn.userservice.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByIdAndIsActive(int employeeId, boolean isActive);
    Optional<Employee> findByEmailAndIsActive(String email, Boolean isActive);
    Optional<Employee> findByCccdAndIsActive(String cccd, Boolean isActive);

    @Query(
            value = """
            SELECT e FROM Employee e where 
            (:startDate IS NULL OR e.lastLogin >= :startDate) AND 
            (:endDate IS NULL OR e.lastLogin <= :endDate) AND
            (:email IS NULL OR e.email in :email) AND 
            (:search IS NULL OR e.name LIKE CONCAT('%', :search, '%') OR e.cccd LIKE CONCAT('%', :search, '%') OR e.email LIKE CONCAT('%', :search, '%') ) AND
            (:cccd IS NULL OR e.cccd in :cccd) AND
            (:isActive IS NULL OR e.isActive in :isActive)
            """
    )
    Page<Employee> getEmployeeByFilter(
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<String> email,
            String search,
            List<String> cccd,
            List<Boolean> isActive,
            Pageable pageable
    );

    @Query(
            value = """
            SELECT e FROM Employee e where 
            (:id IS NULL OR e.id = :id) AND 
            (:email IS NULL OR e.email = :email) AND 
            (:cccd IS NULL OR e.cccd = :cccd) AND
            (:isActive IS NULL OR e.isActive = :isActive)
            """
    )
    Optional<Employee> getEmployeeByFilter(Integer id, String email, String phoneNumber, String cccd, Boolean isActive);
}
