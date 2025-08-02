package doctorhoai.learn.foodservice.service.filter;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.VoucherDto;
import doctorhoai.learn.foodservice.dto.filter.EFilterOption;
import doctorhoai.learn.foodservice.dto.filter.FilterOptions;
import doctorhoai.learn.foodservice.model.Voucher;
import doctorhoai.learn.foodservice.model.enums.EStatusFood;
import doctorhoai.learn.foodservice.repository.CategoryRepository;
import doctorhoai.learn.foodservice.repository.FoodRepository;
import doctorhoai.learn.foodservice.repository.SizeRepository;
import doctorhoai.learn.foodservice.repository.VoucherRepository;
import doctorhoai.learn.foodservice.service.voucher.VoucherServiceImpl;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Map<String, Object> getFilter(FilterOptions filterOptions, String state) {
        Map<String, Object> filter = new HashMap<>();
        List<EFilterOption> options = filterOptions.getOptions();
        if( options.contains(EFilterOption.CATEGORY)){
            filter.put("category", categoryRepository.getCategory(state.equals("admin")).stream().map(mapper::covertToCategoryDto));
        }
        if( options.contains(EFilterOption.FOOD)){
            filter.put("food", foodRepository.getFood(state.equals("admin")).stream().map(mapper::covertToFoodDto));
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
        return filter;
    }
}
