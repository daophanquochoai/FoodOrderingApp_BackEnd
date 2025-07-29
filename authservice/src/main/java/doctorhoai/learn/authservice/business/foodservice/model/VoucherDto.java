package doctorhoai.learn.authservice.business.foodservice.model;

import doctorhoai.learn.authservice.feign.userservice.model.UserDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VoucherDto {

    private Integer id;
    @NotBlank(message = "Code can't empty")
    private String code;
    @NotNull(message = "Discount type can't empty")
    private EDiscountType discountType;
    @Min( value = 0, message = "Discount value should high than 0")
    private Integer discountValue;
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
    private String desc;

    private List<FoodDto> foods;
    private List<CategoryDto> categories;

    private List<UserDto> users;
}
