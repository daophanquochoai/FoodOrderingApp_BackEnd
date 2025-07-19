package doctorhoai.learn.inventoryservice.model;

import doctorhoai.learn.inventoryservice.model.audit.BaseModel;
import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ingredients_user")
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

    private Integer quantity;

    @Column(name = "order_item_id")
    private Integer orderItemId;

    @Column(name = "is_active")
    private Boolean isActive;
}
