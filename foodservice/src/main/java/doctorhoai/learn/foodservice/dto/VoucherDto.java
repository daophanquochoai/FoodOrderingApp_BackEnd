package doctorhoai.learn.foodservice.dto;

import doctorhoai.learn.foodservice.dto.userservice.UserDto;
import doctorhoai.learn.foodservice.model.enums.EDiscountType;
import doctorhoai.learn.foodservice.model.enums.EStatusVoucher;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VoucherDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotBlank(message = "Code can't empty")
    private String code;
    @NotNull(message = "Discount type can't empty")
    private EDiscountType discountType;
    private Double maxDiscount;
    @Min( value = 0, message = "Max use should high than 0")
    private Integer maxUse;
    @Min(value = 0, message = "Used count should high than 0")
    private Integer usedCount;
    @NotNull( message = "Start date can't empty")
    private LocalDate startDate;
    @NotNull(message = "End date can't empty")
    private LocalDate endDate;
    private EStatusVoucher status;

    private List<FoodDto> foods;
    private List<CategoryDto> categories;

    private List<UserDto> users;
}
