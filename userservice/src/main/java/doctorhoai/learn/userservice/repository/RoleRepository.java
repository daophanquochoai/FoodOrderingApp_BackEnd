package doctorhoai.learn.userservice.repository;

import doctorhoai.learn.userservice.model.Role;
import doctorhoai.learn.userservice.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(ERole name);
}
