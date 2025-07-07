package doctorhoai.learn.inventoryservice.dto;

import doctorhoai.learn.inventoryservice.model.enums.ETypeHistory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryImportOrExportDto {
    private Integer id;
    @NotNull(message = "Type can't empty")
    private ETypeHistory type;
    @NotBlank(message = "Note can't empty")
    private String note;
    @NotBlank(message = "Bath code can't empty")
    private String bathCode;
    private SourceDto source;
    private Boolean isActive;

    private List<HistoryIngredientsDto> historyIngredients;
}
