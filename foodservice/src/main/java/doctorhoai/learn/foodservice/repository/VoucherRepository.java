package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.model.Voucher;
import doctorhoai.learn.foodservice.model.enums.EStatusVoucher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {

    @Query(
            value = """
            SELECT vu FROM VoucherUser vu WHERE
            vu.userId = :id AND 
            (:status IS NULL OR vu.voucher.status in :status) AND 
            (:search IS NULL OR vu.voucher.code like CONCAT('%', :search, '%'))
            """
    )
    List<Voucher> getVoucherOfUser(
            Integer id,
            List<EStatusVoucher> status,
            String search,
            Pageable pageable
    );

    @Query(
            value = """
            SELECT v FROM Voucher v
            LEFT JOIN FETCH v.voucherUsers vu
            WHERE
            (:userIds IS NULL OR vu.userId IN :userIds) AND 
            (:status IS NULL OR v.status in :status) AND 
            (:search IS NULL OR v.code like CONCAT('%', :search, '%')) AND
            (:max IS NULL OR (:max = true AND v.maxUse = v.usedCount) OR (:max = false AND v.maxUse > v.usedCount)) AND 
            (:forFood IS NULL OR :forFood = false OR (:forFood = true AND size(v.voucherFoods) > 0)) AND 
            (:forCategory IS NULL OR :forCategory = false OR (:forCategory = true AND ( size(v.voucherFoods) > 0)))
            """
    )
    List<Voucher> getAllVouchers(
            List<Integer> userIds,
            Boolean max,
            Boolean forFood,
            Boolean forCategory,
            List<EStatusVoucher> status,
            String search,
            Pageable pageable
    );

    @Query(
            value = """
            SELECT v FROM Voucher v WHERE
            v.code = :code AND v.status = 'ACTIVE'
            """
    )
    Optional<Voucher> getVoucherByCode(String code);
}
