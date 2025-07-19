package doctorhoai.learn.orderservice.dto.voucherservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import doctorhoai.learn.orderservice.dto.userservice.UserDto;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoucherDto {

    private Integer id;
    private String code;
    private EDiscountType discountType;
    private Double maxDiscount;
    private Integer maxUse;
    private Integer usedCount;
    private LocalDate startDate;
    private LocalDate endDate;
    private EStatusVoucher status;

    private List<FoodDto> foods;
    private List<CategoryDto> categories;

    private List<UserDto> users;
}
