package doctorhoai.learn.foodservice.model;

import doctorhoai.learn.foodservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voucher_category")
@Builder
public class VoucherCategory extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JoinColumn(name = "voucher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Voucher voucher;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private String createBy;
}
