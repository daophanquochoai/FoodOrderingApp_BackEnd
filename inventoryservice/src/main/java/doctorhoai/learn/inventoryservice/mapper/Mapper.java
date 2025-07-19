package doctorhoai.learn.inventoryservice.mapper;

import doctorhoai.learn.inventoryservice.dto.*;
import doctorhoai.learn.inventoryservice.model.*;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public SourceDto convertToSourceDto(Source source) {
        return SourceDto.builder()
                .id(source.getId())
                .name(source.getName())
                .address(source.getAddress())
                .phoneNumber(source.getPhoneNumber())
                .email(source.getEmail())
                .link(source.getLink())
                .taxCode(source.getTaxCode())
                .isActive(source.getIsActive())
                .createdAt(source.getCreatedAt())
                .build();
    }

    public Source convertToSource(SourceDto sourceDto) {
        return Source.builder()
                .name(sourceDto.getName())
                .address(sourceDto.getAddress())
                .phoneNumber(sourceDto.getPhoneNumber())
                .email(sourceDto.getEmail())
                .link(sourceDto.getLink())
                .taxCode(sourceDto.getTaxCode())
                .isActive(sourceDto.getIsActive())
                .build();
    }

    public HistoryImportOrExportDto convertToHistoryImportOrExportDto(HistoryImportOrExport historyImportOrExport) {
        return HistoryImportOrExportDto.builder()
                .id(historyImportOrExport.getId())
                .type(historyImportOrExport.getType())
                .note(historyImportOrExport.getNote())
                .bathCode(historyImportOrExport.getBath_code())
                .isActive(historyImportOrExport.getIsActive())
                .build();
    }

    public HistoryImportOrExport convertToHistoryImportOrExport(HistoryImportOrExportDto historyImportOrExportDto) {
        return HistoryImportOrExport.builder()
                .type(historyImportOrExportDto.getType())
                .note(historyImportOrExportDto.getNote())
                .bath_code(historyImportOrExportDto.getBathCode())
                .isActive(historyImportOrExportDto.getIsActive())
                .build();
    }

    public FoodIngredients convertToFoodIngredients(FoodIngredientsDto foodIngredientsDto) {
        return FoodIngredients.builder()
                .quantityPerUnit(foodIngredientsDto.getQuantityPerUnit())
                .isActive(foodIngredientsDto.getIsActive())
                .foodId(foodIngredientsDto.getFoodId())
                .build();
    }

    public FoodIngredientsDto convertToFoodIngredientsDto(FoodIngredients foodIngredients) {
        return FoodIngredientsDto.builder()
                .quantityPerUnit(foodIngredients.getQuantityPerUnit())
                .isActive(foodIngredients.getIsActive())
                .build();
    }

    public HistoryIngredients convertToHistoryIngredients(HistoryIngredientsDto historyIngredientsDto){
        return HistoryIngredients.builder()
                .quantity(historyIngredientsDto.getQuantity())
                .usedUnit(historyIngredientsDto.getUsedUnit())
                .pricePerUnit(historyIngredientsDto.getPricePerUnit())
                .avgPrice(historyIngredientsDto.getAvgPrice())
                .unit(historyIngredientsDto.getUnit())
                .expiredTime(historyIngredientsDto.getExpiredTime())
                .isActive(historyIngredientsDto.getIsActive())
                .build();
    }

    public HistoryIngredientsDto convertToHistoryIngredientsDto(HistoryIngredients historyIngredients) {
        return HistoryIngredientsDto.builder()
                .id(historyIngredients.getId())
                .quantity(historyIngredients.getQuantity())
                .usedUnit(historyIngredients.getUsedUnit())
                .pricePerUnit(historyIngredients.getPricePerUnit())
                .avgPrice(historyIngredients.getAvgPrice())
                .unit(historyIngredients.getUnit())
                .expiredTime(historyIngredients.getExpiredTime())
                .isActive(historyIngredients.getIsActive())
                .build();
    }

    public IngredientError convertToIngredientError(IngredientsErrorDto ingredientsErrorDto) {
        return IngredientError.builder()
                .id(ingredientsErrorDto.getId())
                .unit(ingredientsErrorDto.getUnit())
                .quantity(ingredientsErrorDto.getQuantity())
                .reason(ingredientsErrorDto.getReason())
                .isActive(ingredientsErrorDto.getIsActive())
                .build();
    }

    public IngredientsErrorDto convertToIngredientsErrorDto(IngredientError ingredientError) {
        return IngredientsErrorDto.builder()
                .id(ingredientError.getId())
                .unit(ingredientError.getUnit())
                .quantity(ingredientError.getQuantity())
                .reason(ingredientError.getReason())
                .isActive(ingredientError.getIsActive())
                .build();
    }

    public Ingredients convertToIngredients(IngredientsDto ingredientsDto) {
        return Ingredients.builder()
                .name(ingredientsDto.getName())
                .unit(ingredientsDto.getUnit())
                .lowThreshold(ingredientsDto.getLowThreshold())
                .isActive(ingredientsDto.getIsActive())
                .build();
    }

    public IngredientsDto convertToIngredientsDto(Ingredients ingredients) {
        return IngredientsDto.builder()
                .id(ingredients.getId())
                .name(ingredients.getName())
                .unit(ingredients.getUnit())
                .lowThreshold(ingredients.getLowThreshold())
                .isActive(ingredients.getIsActive())
                .build();
    }
}
