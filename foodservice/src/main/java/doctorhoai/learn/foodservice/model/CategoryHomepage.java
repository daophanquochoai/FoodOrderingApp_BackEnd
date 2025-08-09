package doctorhoai.learn.foodservice.model;

import doctorhoai.learn.foodservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category_homepage")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryHomepage extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private Integer createBy;
}
