package doctorhoai.learn.userservice.repository;

import doctorhoai.learn.userservice.model.Employee;
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
            SELECT e FROM Employee e WHERE
            (:search IS NULL OR e.name like CONCAT('%',:search,'%') OR e.cccd like CONCAT('%',:search,'%') OR e.email like CONCAT('%',:search,'%')) AND 
            (:isActive IS NULL OR e.isActive = :isActive) AND 
            (:isLogin IS NULL OR (:isLogin = true AND e.lastLogin IS NOT NULL) OR  (:isLogin = false AND e.lastLogin IS NULL)) AND 
            (:startDate IS NULL OR e.createdAt > :startDate) AND
            (:endDate IS NULL OR e.createdAt < :endDate)
            """
    )
    List<Employee> getListByFilter(
            String search,
            Boolean isLogin,
            Boolean isActive,
            LocalDateTime startDate,
            LocalDateTime endDate
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
