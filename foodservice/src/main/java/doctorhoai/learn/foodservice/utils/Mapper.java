package doctorhoai.learn.foodservice.utils;

import doctorhoai.learn.foodservice.dto.*;
import doctorhoai.learn.foodservice.model.*;
import org.springframework.stereotype.Service;

@Service
public class Mapper {
    public VoucherDto convertToVoucherDto(Voucher voucher) {
        return VoucherDto.builder()
                .id(voucher.getId())
                .code(voucher.getCode())
                .discountType(voucher.getDiscountType())
                .maxDiscount(voucher.getMaxDiscount())
                .maxUse(voucher.getMaxUse())
                .usedCount(voucher.getUsedCount())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .status(voucher.getStatus())
                .build();
    }
    public Voucher convertToVoucher(VoucherDto voucher) {
        return Voucher.builder()
                .code(voucher.getCode())
                .discountType(voucher.getDiscountType())
                .maxDiscount(voucher.getMaxDiscount())
                .maxUse(voucher.getMaxUse())
                .usedCount(voucher.getUsedCount())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .status(voucher.getStatus())
                .build();
    }

    public FoodDto covertToFoodDto(Food food) {
        return FoodDto.builder()
                .id(food.getId())
                .name(food.getName())
                .image(food.getImage())
                .desc(food.getDesc())
                .status(food.getStatus())
                .build();
    }

    public Food convertToFood(FoodDto foodDto) {
        return Food.builder()
                .name(foodDto.getName())
                .image(foodDto.getImage())
                .desc(foodDto.getDesc())
                .status(foodDto.getStatus())
                .build();
    }

    public CategoryDto covertToCategoryDto_Stack(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .desc(category.getDesc())
                .status(category.getStatus())
                .parent(category.getParentId() != null ? covertToCategoryDto_Stack(category.getParentId()) : null)
                .build();
    }

    public CategoryDto covertToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .desc(category.getDesc())
                .status(category.getStatus())
                .build();
    }

    public Category covertToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .image(categoryDto.getImage())
                .desc(categoryDto.getDesc())
                .status(categoryDto.getStatus())
                .build();
    }

    public SizeDto convertToSizeDto(Size size){
        return SizeDto.builder()
                .id(size.getId())
                .name(size.getName())
                .isActive(size.getIsActive())
                .build();
    }

    public Size convertToSize(SizeDto sizeDto) {
        return Size.builder()
                .name(sizeDto.getName())
                .isActive(sizeDto.getIsActive())
                .build();
    }

    public FoodSize convertToFoodSize(FoodSizeDto foodSizeDto){
        return FoodSize.builder()
                .discount(foodSizeDto.getDiscount())
                .readyInMinutes(foodSizeDto.getReadyInMinutes())
                .price(foodSizeDto.getPrice())
                .isActive(foodSizeDto.getIsActive())
                .build();
    }

    public FoodSizeDto convertToFoodSizeDto(FoodSize foodSize) {
        return FoodSizeDto.builder()
                .discount(foodSize.getDiscount())
                .readyInMinutes(foodSize.getReadyInMinutes())
                .price(foodSize.getPrice())
                .isActive(foodSize.getIsActive())
                .id(foodSize.getId())
                .build();
    }

}
