package doctorhoai.learn.aiservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodSizeDto implements Serializable {
    private Integer id;
    private Float discount;
    private Float readyInMinutes;
    private Float price;
    private FoodDto foodId;
    private SizeDto sizeId;
    private Boolean isActive;
}
