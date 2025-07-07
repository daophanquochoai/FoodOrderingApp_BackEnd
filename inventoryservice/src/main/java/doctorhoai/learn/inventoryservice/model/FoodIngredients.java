package doctorhoai.learn.inventoryservice.model;

import doctorhoai.learn.inventoryservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food_ingredients")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodIngredients extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "food_id")
    private Integer foodId;

    @JoinColumn( name = "ingredients_id")
    @ManyToOne
    private Ingredients ingredients;

    @Column(name = "quantity_per_unit")
    private Float quantityPerUnit;

    @Column(name = "is_active")
    private Boolean isActive;
}
