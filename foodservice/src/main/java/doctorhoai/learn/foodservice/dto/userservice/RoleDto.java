package doctorhoai.learn.foodservice.dto.userservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDto {
    private Integer id;
    private ERole roleName;
}
