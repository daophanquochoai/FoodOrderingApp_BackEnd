package doctorhoai.learn.userservice.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {

    @CreatedDate
    @Column(updatable = false, name = "create_at")
    @JsonIgnore
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "late_update_time")
    @JsonIgnore
    private LocalDateTime lateUpdateTime;

}
