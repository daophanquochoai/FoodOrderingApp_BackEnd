package doctorhoai.learn.authservice.business.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private Boolean isActive;

    //history import or export
    private List<Integer> sourceId;
    private Boolean inventory;
    private List<Integer> ingredientsId;
    private Float minPrice;
    private Float maxPrice;
    private ETypeHistory type;
    private List<Integer> historyImportOrExportId;

    //ingredients
    private List<String> unit;
    private Integer minThreshold;
    private Integer maxThreshold;

    //ingredients error
    private Integer minQuantity;
    private Integer maxQuantity;
    private List<EUnitType> units;

}
