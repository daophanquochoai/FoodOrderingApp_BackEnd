package doctorhoai.learn.foodservice.model;

import doctorhoai.learn.foodservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "voucher_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherUser extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @JoinColumn(name = "voucher_id")
    @ManyToOne
    private Voucher voucher;

    private Integer quantity;

    @Column(name = "is_active")
    private Boolean isActive;

    // TODO : chua xu ly
    @Column(name = "create_by")
    private String createBy;
}
