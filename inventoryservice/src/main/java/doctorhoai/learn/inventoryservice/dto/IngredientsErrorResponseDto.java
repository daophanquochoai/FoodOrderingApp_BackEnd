package doctorhoai.learn.inventoryservice.dto;

import doctorhoai.learn.inventoryservice.model.enums.EUnitType;

public class IngredientsErrorResponseDto {
    private Integer id;
    private EUnitType unit;
    private Integer quantity;
    private String reason;
    private Boolean isActive;

    private HistoryImportOrExportDto batchCode;
    private String name;
    private Integer historyId;

    // Constructors
    public IngredientsErrorResponseDto() {}

    public IngredientsErrorResponseDto(Integer id, EUnitType unit, Integer quantity, String reason, Boolean isActive, HistoryImportOrExportDto batchCode, String name, Integer historyId) {
        this.id = id;
        this.unit = unit;
        this.quantity = quantity;
        this.reason = reason;
        this.isActive = isActive;
        this.batchCode = batchCode;
        this.name = name;
        this.historyId = historyId;
    }

    // Getter and Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public EUnitType getUnit() { return unit; }
    public void setUnit(EUnitType unit) { this.unit = unit; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public HistoryImportOrExportDto getBatchCode() { return batchCode; }
    public void setBatchCode(HistoryImportOrExportDto batchCode) { this.batchCode = batchCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getHistoryId() { return historyId; }
    public void setHistoryId(Integer historyId) { this.historyId = historyId; }
}
