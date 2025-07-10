package doctorhoai.learn.orderservice.feign.dto;

import doctorhoai.learn.orderservice.dto.voucherservice.EStatusCategory;
import doctorhoai.learn.orderservice.dto.voucherservice.EStatusFood;
import doctorhoai.learn.orderservice.dto.voucherservice.EStatusVoucher;
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
public class FilterFood {
    private Integer pageNo;
    private Integer pageSize;
    private String search;
    private String sort;
    private String order;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer deep;
    private List<Integer> id;

    //voucher
    private List<EStatusVoucher> statusVouchers;
    private Integer userId;
    private Boolean max;
    private Boolean forFood;
    private Boolean forCategory;

    //food
    private List<EStatusFood> statusFoods;

    private List<EStatusCategory> statusCategories;
}
