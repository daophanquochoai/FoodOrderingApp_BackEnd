package doctorhoai.learn.inventoryservice.model;

import doctorhoai.learn.inventoryservice.model.audit.BaseModel;
import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ingredients")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Ingredients extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private EUnitType unit;

    @Column(name = "low_threshold")
    private Integer lowThreshold;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private Integer createBy;
}
