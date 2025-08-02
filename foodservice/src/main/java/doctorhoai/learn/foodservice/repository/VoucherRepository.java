package doctorhoai.learn.foodservice.repository;

import doctorhoai.learn.foodservice.dto.VoucherDto;
import doctorhoai.learn.foodservice.model.Voucher;
import doctorhoai.learn.foodservice.model.enums.EStatusVoucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @EntityGraph(attributePaths = {"voucherFoods", "voucherCategories"})
    List<Voucher> getVoucherOfUser(
            Integer id,
            List<EStatusVoucher> status,
            String search,
            Pageable pageable
    );

    @Query("""
    SELECT v FROM Voucher v
    WHERE
    (:status IS NULL OR v.status IN :status) AND 
    (:search IS NULL OR v.code LIKE CONCAT('%', :search, '%')) AND
    (:max IS NULL OR (:max = true AND v.maxUse = v.usedCount) OR (:max = false AND v.maxUse > v.usedCount)) AND 
    (:forCategory IS NULL OR :forCategory = false OR (:forCategory = true AND size(v.voucherCategories) > 0)) AND 
    (
        (:foodIds IS NULL OR EXISTS (
            SELECT 1 FROM VoucherFood vf WHERE vf.voucher = v AND vf.food.id IN :foodIds AND vf.isActive = true
        )) OR
        (:userIds IS NULL OR EXISTS (
            SELECT 1 FROM VoucherUser vu WHERE vu.voucher = v AND vu.userId IN :userIds
        )) OR
        (:categoryIds IS NULL OR EXISTS (
            SELECT 1 FROM VoucherCategory vc WHERE vc.voucher = v AND vc.category.id IN :categoryIds AND vc.isActive = true
        ))
    )
""")
    Page<Voucher> getAllVouchers(
            @Param("userIds") List<Integer> userIds,
            @Param("max") Boolean max,
            @Param("forFood") Boolean forFood,
            @Param("forCategory") Boolean forCategory,
            @Param("status") List<EStatusVoucher> status,
            @Param("foodIds") List<Integer> foodIds,
            @Param("categoryIds") List<Integer> categoryIds,
            @Param("search") String search,
            Pageable pageable
    );


    @Query(
            value = """
            SELECT v FROM Voucher v WHERE
            v.code = :code AND v.status = 'ACTIVE'
            """
    )
    Optional<Voucher> getVoucherByCode(String code);

    Optional<Voucher> getVoucherByIdAndStatus(Integer id, EStatusVoucher status);

    @Query(
            value = """
            SELECT v FROM Voucher v WHERE
            (:isActive IS NULL OR v.status = :isActive) AND
            (:ids IS NULL OR v.id IN :ids)
            """
    )
    List<Voucher> getVoucherByIds(List<Integer> ids, Boolean isActive);

    @Query("""
    SELECT v FROM Voucher v
    WHERE 
        v.code = :code AND 
        v.status = 'ACTIVE' AND 
        v.endDate > CURRENT_DATE
    """)
    Optional<Voucher> findVoucherByCode(@Param("code") String code);

    @Query(
            value = """
            SELECT v FROM Voucher v 
            WHERE (:state IS TRUE OR v.status = 'ACTIVE')
            """
    )
    List<Voucher> getVoucherForOption(Boolean state);

}
