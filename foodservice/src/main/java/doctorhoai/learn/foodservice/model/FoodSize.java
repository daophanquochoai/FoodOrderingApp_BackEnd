package doctorhoai.learn.foodservice.model;

import doctorhoai.learn.foodservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food_size")
@Builder
public class FoodSize extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float discount;

    @Column(name = "ready_in_minutes")
    private Float readyInMinutes;

    @Column(name = "is_active")
    private Boolean isActive;
    private Float price;

    @JoinColumn(name = "size_id")
    @ManyToOne
    private Size size;

    @JoinColumn(name = "food_id")
    @ManyToOne
    private Food food;

    @Column(name = "create_by")
    private String createBy;
}
