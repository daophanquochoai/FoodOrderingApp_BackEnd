package doctorhoai.learn.orderservice.model;

import doctorhoai.learn.orderservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItem extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "cart_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Cart cartId;

    @Column(name = "food_id")
    private Integer foodId;

    private Integer quantity;

    @Column(name = "price_at_time")
    private Float priceAtTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by") // TODO : chua xu ly
    private Integer createdBy;
}
