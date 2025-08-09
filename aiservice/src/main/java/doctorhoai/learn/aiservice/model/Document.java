package doctorhoai.learn.aiservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "document")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Long size;

    private String url;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private Integer createBy;

    @CreatedDate
    @Column(updatable = false, name = "create_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "late_update_time")
    @JsonIgnore
    private LocalDateTime lateUpdateTime;
}
