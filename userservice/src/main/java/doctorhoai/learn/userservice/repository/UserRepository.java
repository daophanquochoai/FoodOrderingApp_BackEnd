package doctorhoai.learn.userservice.repository;

import doctorhoai.learn.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailAndIsActive(String email, Boolean isActive);
    Optional<User> findByIdAndIsActive(Integer id, Boolean isActive);
    @Query(
            value = """
            SELECT u FROM User u where 
            (:id IS NULL OR u.id = :id) AND 
            (:email IS NULL OR u.email = :email) AND 
            (:phoneNumber IS NULL OR u.phoneNumber = :phoneNumber) AND 
            (:isActive IS NULL OR u.isActive = :isActive)
            """
    )
    Optional<User> getUserByFilter(Integer id, String email, String phoneNumber, Boolean isActive);
    Optional<User> findByPhoneNumberAndIsActive(String phoneNumber, Boolean isActive);

    @Query(
            value = """
            SELECT u FROM User u WHERE
            (:search IS NULL OR u.name like CONCAT('%',:search,'%') OR u.phoneNumber like CONCAT('%',:search,'%') OR u.email like CONCAT('%',:search,'%')) AND 
            (:isActive IS NULL OR u.isActive = :isActive) AND 
            (:isLogin IS NULL OR (:isLogin = true AND u.lastLogin IS NOT NULL) OR  (:isLogin = false AND u.lastLogin IS NULL)) AND 
            (:startDate IS NULL OR u.createdAt > :startDate) AND
            (:endDate IS NULL OR u.createdAt < :endDate)
            """
    )
    List<User> getListByUser(
            String search,
            Boolean isLogin,
            Boolean isActive,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
