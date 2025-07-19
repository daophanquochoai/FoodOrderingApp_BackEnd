package doctorhoai.learn.foodservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotBlank(message = "Name can't empty")
    private String name;
    private Boolean isActive;
}
