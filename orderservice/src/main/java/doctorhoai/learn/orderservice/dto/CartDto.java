package doctorhoai.learn.orderservice.dto;

import doctorhoai.learn.orderservice.dto.userservice.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartDto {
    private Integer id;
    private UserDto userId;
    private Boolean isActive;
    private List<CartItemDto> cartItems;
}
