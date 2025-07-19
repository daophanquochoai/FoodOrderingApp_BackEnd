package doctorhoai.learn.inventoryservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SourceDto {
    private Integer id;
    @NotBlank(message = "Name can't empty")
    private String name;
    @NotBlank(message = "Address can't empty")
    private String address;
    @NotBlank(message = "Phone number can't empty")
    @Length(min = 10, max = 12, message = "Phone number should have 10-12 digits")
    private String phoneNumber;
    @NotBlank(message = "Email can't empty")
    @Email(message = "Invalid email format")
    private String email;
    private String link;
    @NotBlank(message = "Tax code can't empty")
    private String taxCode;
    private Boolean isActive;
    private LocalDateTime createdAt;

    private List<HistoryImportOrExportDto> history;
}
