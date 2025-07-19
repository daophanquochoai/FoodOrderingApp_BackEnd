package doctorhoai.learn.inventoryservice.model;

import doctorhoai.learn.inventoryservice.model.audit.BaseModel;
import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ingredients_error")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IngredientError extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "history_ingredients_id")
    @ManyToOne
    private HistoryIngredients historyIngredients;

    @Enumerated(EnumType.STRING)
    private EUnitType unit;

    private Integer quantity;

    private String reason;

    @Column(name = "is_active")
    private Boolean isActive;
}
