package doctorhoai.learn.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import doctorhoai.learn.userservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employee")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cccd", nullable = false)
    private String cccd;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name ="password", nullable = false)
    private String password;

    @Column(name ="last_login")
    private LocalDateTime lastLogin;

    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // TODO: Chua xu ly
    @CreatedBy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private Employee createBy;
}
