package doctorhoai.learn.foodservice.service.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.dto.FoodSizeDto;
import doctorhoai.learn.foodservice.dto.VoucherDto;
import doctorhoai.learn.foodservice.dto.filter.EFilterOption;
import doctorhoai.learn.foodservice.dto.filter.FilterOptions;
import doctorhoai.learn.foodservice.dto.ingredients.IngredientsDto;
import doctorhoai.learn.foodservice.feign.ingredients.IngredientFeign;
import doctorhoai.learn.foodservice.model.FoodSize;
import doctorhoai.learn.foodservice.model.Size;
import doctorhoai.learn.foodservice.model.Voucher;
import doctorhoai.learn.foodservice.repository.CategoryRepository;
import doctorhoai.learn.foodservice.repository.FoodRepository;
import doctorhoai.learn.foodservice.repository.SizeRepository;
import doctorhoai.learn.foodservice.repository.VoucherRepository;
import doctorhoai.learn.foodservice.service.voucher.VoucherServiceImpl;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FilterService {

    private final CategoryRepository categoryRepository;
    private final FoodRepository foodRepository;
    private final SizeRepository sizeRepository;
    private final VoucherRepository voucherRepository;
    private final Mapper mapper;
    private final VoucherServiceImpl voucherService;
    private final IngredientFeign ingredientFeign;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> getFilter(FilterOptions filterOptions, String state) {
        Map<String, Object> filter = new HashMap<>();
        List<EFilterOption> options = filterOptions.getOptions();
        if( options.contains(EFilterOption.CATEGORY)){
            filter.put("category", categoryRepository.getCategory(state.equals("admin")).stream().map(mapper::covertToCategoryDto));
        }
        if( options.contains(EFilterOption.FOOD)){
            List<FoodDto> foodDtos =  foodRepository.getFood(state.equals("admin")).stream().map( item -> {
                FoodDto itemDto = mapper.covertToFoodDto(item);
                if( item.getFoodSizes() !=null && !item.getFoodSizes().isEmpty() ){
                    List<FoodSizeDto> foodSizeDtos = new ArrayList<>();
                    for( FoodSize foodSize : item.getFoodSizes()){
                        if( foodSize.getIsActive()){
                            FoodSizeDto foodSizeDto = mapper.convertToFoodSizeDto(foodSize);
                            if( foodSize.getSize() != null){
                                foodSizeDto.setSizeId(mapper.convertToSizeDto(foodSize.getSize()));
                            }
                            foodSizeDtos.add(foodSizeDto);
                        }
                    }
                    itemDto.setFoodSizes(foodSizeDtos);
                }
                return itemDto;
            }).toList();
            filter.put("food", foodDtos);
        }
        if( options.contains(EFilterOption.SIZE)){
            filter.put("size", sizeRepository.getSize(state.equals("admin")).stream().map(mapper::convertToSizeDto));
        }
        if( options.contains(EFilterOption.VOUCHER)){
            List<Voucher> vouchers = voucherRepository.getVoucherForOption(state.equals("admin"));
            List<VoucherDto> voucherDtos = vouchers.stream().map(i -> {
                VoucherDto voucherDto = mapper.convertToVoucherDto(i);
                if( i.getVoucherFoods() != null && !i.getVoucherFoods().isEmpty()){
                    voucherDto.setFoods(voucherService.convertListVoucherFood_ToFood(i.getVoucherFoods()));
                }
                if( i.getVoucherCategories() != null && !i.getVoucherCategories().isEmpty()){
                    voucherDto.setCategories(voucherService.convertListVoucherCategory_ToCategory(i.getVoucherCategories()));
                }
                return voucherDto;
            }).toList();
            filter.put("voucher",voucherDtos);
        }
        if( options.contains(EFilterOption.INGREDIENTS)){
            ResponseEntity<ResponseObject> response = ingredientFeign.getIngredients();
            if( response.getStatusCode().is2xxSuccessful() ){
                List<IngredientsDto> ingredientsDtoList = objectMapper.convertValue(response.getBody().getData(), new TypeReference<List<IngredientsDto>>() {});
                filter.put("ingredients",ingredientsDtoList);
            }
        }
        return filter;
    }
}
