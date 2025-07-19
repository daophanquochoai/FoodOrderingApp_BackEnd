package doctorhoai.learn.orderservice.model;

import doctorhoai.learn.orderservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "history_point")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryPoint extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "used_point")
    private Integer usedPoint;

    @JoinColumn(name = "order_id")
    @OneToOne
    private Order orderId;

    @Column(name = "create_by")
    private Integer createBy;
}
