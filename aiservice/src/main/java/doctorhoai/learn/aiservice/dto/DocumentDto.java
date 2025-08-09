package doctorhoai.learn.aiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class DocumentDto {
    private Integer id;
    private String name;
    private Long size;
    private String url;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

