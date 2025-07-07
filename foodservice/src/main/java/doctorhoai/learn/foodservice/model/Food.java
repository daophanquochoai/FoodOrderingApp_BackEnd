package doctorhoai.learn.foodservice.model;

import doctorhoai.learn.foodservice.model.audit.BaseModel;
import doctorhoai.learn.foodservice.model.enums.EStatusFood;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Food extends BaseModel implements Serializable {
    private final static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String image;
    @Column(name = "`desc`")
    private String desc;
    @Enumerated(EnumType.STRING)
    private EStatusFood status;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;
    private String createBy;
}
