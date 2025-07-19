package doctorhoai.learn.orderservice.model;

import doctorhoai.learn.orderservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_item")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItem extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order orderId;

    @Column(name = "food_id")
    private Integer foodId;

    private Integer quantity;

    @Column(name = "price_at_time")
    private Float priceAtTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private Integer createBy;
}
