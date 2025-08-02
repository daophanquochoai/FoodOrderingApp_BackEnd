package doctorhoai.learn.foodservice.model;

import doctorhoai.learn.foodservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "voucher_food")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VoucherFood extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private String createdBy;
}
