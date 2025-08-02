package doctorhoai.learn.inventoryservice.dto;

import doctorhoai.learn.inventoryservice.model.enums.ETypeHistory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryImportOrExportDto {
    private Integer id;
    private ETypeHistory type;
    private String note;
    private String bathCode;
    private SourceDto source;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private List<HistoryIngredientsDto> historyIngredients;
}
