package doctorhoai.learn.foodservice.model;

import doctorhoai.learn.foodservice.model.audit.BaseModel;
import doctorhoai.learn.foodservice.model.enums.EDiscountType;
import doctorhoai.learn.foodservice.model.enums.EStatusVoucher;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "voucher")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voucher extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    @Column(name = "`desc`")
    private String desc;

    @Column(name = "discount_type")
    @Enumerated(EnumType.STRING)
    private EDiscountType discountType;

    @Column(name = "max_discount")
    private Double maxDiscount;

    @Column(name = "max_use")
    private Integer maxUse;

    @Column(name = "used_count")
    private Integer usedCount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private EStatusVoucher status;

    //TODO : chua xu ly
    @Column(name = "create_by")
    private String createBy;

    // list
    @OneToMany(mappedBy = "voucher")
    private Set<VoucherFood> voucherFoods;

    @OneToMany(mappedBy = "voucher")
    private Set<VoucherCategory> voucherCategories;

    @OneToMany(mappedBy = "voucher")
    private Set<VoucherUser> voucherUsers;
}
