package doctorhoai.learn.foodservice.dto.filter;

import doctorhoai.learn.foodservice.model.enums.EStatusCategory;
import doctorhoai.learn.foodservice.model.enums.EStatusFood;
import doctorhoai.learn.foodservice.model.enums.EStatusVoucher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filter {
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
    private List<Integer> categoryIds;

    //food
    private List<EStatusFood> statusFoods;
    private List<Integer> categoryId;

    private List<EStatusCategory> statusCategories;

    //size
    private Boolean isActive;

    // foodSize
    private Float minDiscount;
    private Float maxDiscount;
    private Float minPrice;
    private Float maxPrice;
    private Float minReady;
    private Float maxReady;
    private List<Integer> foodIds;
    private List<Integer> sizeIds;
}
