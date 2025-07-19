package doctorhoai.learn.userservice.dto.Filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterUser {
    private Integer id;
    private String email;
    private String phoneNumber;
    private String cccd;
    private Boolean isActive;
}
