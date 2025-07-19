package doctorhoai.learn.aiservice.dto;

import doctorhoai.learn.aiservice.model.enums.EStatusCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String image;
    private String desc;
    private EStatusCategory status;
    private CategoryDto parent;

    private List<FoodDto> foods;
}
