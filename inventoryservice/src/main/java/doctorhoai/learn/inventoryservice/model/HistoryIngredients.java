package doctorhoai.learn.inventoryservice.model;

import doctorhoai.learn.inventoryservice.model.audit.BaseModel;
import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "history_ingredients")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryIngredients extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "ingredients_id")
    @ManyToOne
    private Ingredients ingredientsId;

    @JoinColumn(name = "history_id")
    @ManyToOne
    private HistoryImportOrExport history;

    private Integer quantity;

    @Column(name = "used_unit")
    private Integer usedUnit;

    @Column(name = "price_per_unit")
    private Float pricePerUnit;

    @Column(name = "avg_price")
    private Float avgPrice;

    @Enumerated(EnumType.STRING)
    private EUnitType unit;

    @Column(name = "expired_time")
    private LocalDate expiredTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private Integer createBy;

    //list
    @OneToMany(mappedBy = "historyIngredients")
    private List<IngredientError> errors;


}
