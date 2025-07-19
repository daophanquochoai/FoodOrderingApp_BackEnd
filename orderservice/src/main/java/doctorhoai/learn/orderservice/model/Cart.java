package doctorhoai.learn.orderservice.model;

import doctorhoai.learn.orderservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cart extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private Integer createBy;

    @OneToMany(mappedBy = "cartId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
}
