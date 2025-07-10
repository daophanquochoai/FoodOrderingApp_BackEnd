package doctorhoai.learn.orderservice.model;

import doctorhoai.learn.orderservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shipping_fee_config")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShippingFeeConfig extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "base_fee")
    private Float baseFee;

    @Column(name = "fee_per_km")
    private Float feePerKm;

    @Column(name = "rush_hour_fee")
    private Float rushHourFee;

    @Column(name = "min_order_for_fee_shipping")
    private Float minOrderForFeeShipping;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private Integer createBy;
}
