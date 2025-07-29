package doctorhoai.learn.userservice.repository;

import doctorhoai.learn.userservice.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {


    @Query(
            value = """
            SELECT a FROM Address a WHERE 
            (:search IS NULL OR a.address like concat('%', :search, '%') OR a.commune like concat('%', :search, '%') OR a.province like concat('%', :search, '%')) AND 
            (:isActive IS NULL OR a.isActive = :isActive) AND 
            (:userId IS NULL OR a.userId.id = :userId)
            """
    )
    Page<Address> getAddressByFilter(
            String search,
            Boolean isActive,
            Integer userId,
            Pageable pageable
    );

    Optional<Address> getAddressByIdAndIsActive(Integer id, Boolean isActive);
}
