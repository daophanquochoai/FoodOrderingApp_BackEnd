package doctorhoai.learn.orderservice.model;


import doctorhoai.learn.orderservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "point")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Point extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "is_active")
    private Boolean isActive;

    private Integer point;

    @Column(name = "create_by")
    private Integer createBy;
}
