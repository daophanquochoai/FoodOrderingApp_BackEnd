package doctorhoai.learn.userservice.dto.Filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterUser {
    private Integer id;
    private String email;
    private String phoneNumber;
    private String cccd;
    private Boolean isActive;
}
