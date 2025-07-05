package doctorhoai.learn.foodservice.model;

import doctorhoai.learn.foodservice.model.audit.BaseModel;
import doctorhoai.learn.foodservice.model.enums.EStatusCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private String image;
    private String desc;
    @Enumerated(EnumType.STRING)
    private EStatusCategory status;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentId;

    @Column(name = "create_by")
    private String createBy;
}
