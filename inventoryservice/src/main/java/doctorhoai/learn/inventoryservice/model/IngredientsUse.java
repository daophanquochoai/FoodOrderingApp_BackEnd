package doctorhoai.learn.inventoryservice.model;

import doctorhoai.learn.inventoryservice.model.audit.BaseModel;
import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ingredients_use")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IngredientsUse extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "history_ingredients_id")
    @ManyToOne
    private HistoryIngredients historyIngredients;

    @Enumerated(EnumType.STRING)
    private EUnitType unit;

    private Float quantity;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "is_active")
    private Boolean isActive;

}
