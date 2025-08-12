package doctorhoai.learn.orderservice.model;

import doctorhoai.learn.orderservice.model.audit.BaseModel;
import doctorhoai.learn.orderservice.model.enums.EStatusOrder;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "`order`")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "total_price")
    private Float totalPrice;

    @JoinColumn(name = "payment_id")
    @ManyToOne
    private Payment paymentId;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "discount_applied")
    private Integer discountApplied;

    private String address;

    @Column(name = "ship_fee")
    private Float shipFee;

    @Enumerated(EnumType.STRING)
    private EStatusOrder status;

    @Column(name = "table_number")
    private Integer tableNumber;

    @Column(name = "shipper_id")
    private Integer shipperId;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String message;

    private Double cogs;


    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
