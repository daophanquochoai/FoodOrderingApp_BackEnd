package doctorhoai.learn.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class AddressDto {
    private Integer id;
    private UserDto userId;
    private String province;
    private String commune;
    private String address;
    private Boolean isDefault;
    private Boolean isActive;
    private String phoneNumber;
    private String name;
}
