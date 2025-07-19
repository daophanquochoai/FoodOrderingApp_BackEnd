package doctorhoai.learn.foodservice.model;

import doctorhoai.learn.foodservice.model.audit.BaseModel;
import doctorhoai.learn.foodservice.model.enums.EStatusCategory;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String image;
    @Column(name = "`desc`")
    private String desc;
    @Enumerated(EnumType.STRING)
    private EStatusCategory status;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentId;

    @Column(name = "create_by")
    private String createBy;

    //list
    @OneToMany(mappedBy = "category")
    private List<Food> foods;
}
