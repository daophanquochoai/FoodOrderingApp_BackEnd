package doctorhoai.learn.foodservice.service.voucher;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.CategoryDto;
import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.dto.VoucherDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.dto.userservice.UserDto;
import doctorhoai.learn.foodservice.exception.CategoryNotFoundException;
import doctorhoai.learn.foodservice.exception.FoodNotFoundException;
import doctorhoai.learn.foodservice.exception.UserNotFoundException;
import doctorhoai.learn.foodservice.exception.VoucherNotFoundException;
import doctorhoai.learn.foodservice.feign.userservice.UserFeign;
import doctorhoai.learn.foodservice.model.*;
import doctorhoai.learn.foodservice.model.enums.EStatusVoucher;
import doctorhoai.learn.foodservice.repository.CategoryRepository;
import doctorhoai.learn.foodservice.repository.FoodRepository;
import doctorhoai.learn.foodservice.repository.VoucherRepository;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final UserFeign userFeign;
    private final Mapper mapper;


    @Override
    public List<VoucherDto> getVoucherOfUser(Filter filter) {
        ResponseEntity<ResponseObject> responseUser = userFeign.getUserById(filter.getUserId());
        if( responseUser.getStatusCode().is2xxSuccessful()){
            throw new UserNotFoundException();
        }
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }
        List<Voucher> vouchers = voucherRepository.getVoucherOfUser(filter.getUserId(), filter.getStatusVouchers(), filter.getSearch(), pageable);
        return convertListVoucher(vouchers);
    }

    @Override
    public PageObject getAllVouchers(Filter filter) {
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }
        Page<Voucher> vouchers = voucherRepository.getAllVouchers(
            filter.getId() == null || filter.getId().isEmpty() ? null : filter.getId(),
            filter.getMax(),
            filter.getForFood(),
            filter.getForCategory(),
            filter.getStatusVouchers(),
            filter.getFoodIds() == null || filter.getFoodIds().isEmpty() ? null :filter.getFoodIds(),
            filter.getCategoryIds() == null || filter.getCategoryIds().isEmpty() ? null :filter.getCategoryIds(),
            filter.getSearch(),
            pageable
        );

        return PageObject.builder()
                .page(filter.getPageNo())
                .totalPage(vouchers.getTotalPages())
                .data(convertListVoucher(vouchers.getContent()))
                .build();
    }

    @Override
    @Transactional
    public VoucherDto createVoucher(VoucherDto voucherDto) {
        Voucher voucher = mapper.convertToVoucher(voucherDto);

        // Gắn foods nếu có
        List<FoodDto> foodDtoList = voucherDto.getFoods();
        if (foodDtoList != null && !foodDtoList.isEmpty()) {
            List<Integer> foodIds = foodDtoList.stream().map(FoodDto::getId).toList();
            List<Food> foods = foodRepository.getListFoodByListId(foodIds);

            if (foods.size() != foodIds.size()) {
                throw new FoodNotFoundException();
            }

            Set<VoucherFood> voucherFoods = new HashSet<>();
            for (Food food : foods) {
                VoucherFood voucherFood = VoucherFood.builder()
                        .voucher(voucher)
                        .food(food)
                        .isActive(true)
                        .build();
                voucherFoods.add(voucherFood);
            }

            voucher.setVoucherFoods(voucherFoods);
        }

        // Gắn categories nếu có
        List<CategoryDto> categoryDtoList = voucherDto.getCategories();
        if (categoryDtoList != null && !categoryDtoList.isEmpty()) {
            List<Integer> categoryIds = categoryDtoList.stream().map(CategoryDto::getId).toList();
            List<Category> categories = categoryRepository.getListCategoryByListId(categoryIds);

            if (categories.size() != categoryIds.size()) {
                throw new CategoryNotFoundException();
            }

            Set<VoucherCategory> voucherCategories = new HashSet<>();
            for (Category category : categories) {
                VoucherCategory voucherCategory = VoucherCategory.builder()
                        .voucher(voucher)
                        .category(category)
                        .isActive(true)
                        .build();
                voucherCategories.add(voucherCategory);
            }

            voucher.setVoucherCategories(voucherCategories);
        }

        // Lưu vào DB
        Voucher savedVoucher = voucherRepository.save(voucher);
        return convertVoucher(savedVoucher);

    }

    @Override
    public VoucherDto getVoucherByCode(String code) {
        return convertVoucher(
                voucherRepository.getVoucherByCode(code).orElseThrow(VoucherNotFoundException::new)
        );
    }

    @Override
    public VoucherDto getVoucherById(Integer id) {
        Voucher voucher = voucherRepository.getVoucherByIdAndStatus(id, EStatusVoucher.ACTIVE).orElseThrow(VoucherNotFoundException::new);
        return convertVoucher(voucher);
    }

    @Override
    public String updateVoucher(Integer id) {
        Voucher data = findVoucher(id);
        if( data.getMaxDiscount() > data.getUsedCount() ){
            data.setUsedCount(data.getUsedCount() + 1);
            voucherRepository.save(data);
            return "";
        }else {
            return "Voucher was max";
        }
    }

    public Voucher findVoucher ( Integer id ){
        Optional<Voucher> v = voucherRepository.findById(id);
        if( v.isEmpty()){
            throw new VoucherNotFoundException();
        }
        return v.get();
    }

    @Override
    public String updateRollbackVoucher(Integer id) {
        Voucher data = findVoucher(id);
        if( data.getUsedCount() - 1 > 0){
            data.setUsedCount(data.getUsedCount() - 1);
            voucherRepository.save(data);
            return "";
        }else {
            return "Voucher use equals 0";
        }
    }

    @Override
    public List<VoucherDto> getVoucherByIds(List<Integer> ids) {
        List<Voucher> vouchers = voucherRepository.getVoucherByIds(ids, null);
        return vouchers.stream().map(mapper::convertToVoucherDto).toList();
    }

    private List<VoucherDto> convertListVoucher(List<Voucher> vouchers) {
        return vouchers.stream().map(this::convertVoucher).toList();
    }

    public VoucherDto convertVoucher(Voucher v) {
        VoucherDto voucherDto = mapper.convertToVoucherDto(v);
        if(v.getVoucherFoods() != null && !v.getVoucherFoods().isEmpty()){
            List<FoodDto> foodDtos = new ArrayList<>();
            for( VoucherFood voucherFood : v.getVoucherFoods()){
                if( voucherFood != null){
                    foodDtos.add(mapper.covertToFoodDto(voucherFood.getFood()));
                }
            }
            voucherDto.setFoods(foodDtos);
        }
        if( v.getVoucherCategories() != null && !v.getVoucherCategories().isEmpty()){
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for(VoucherCategory voucherCategory : v.getVoucherCategories()){
                if( voucherCategory != null){
                    categoryDtos.add(mapper.covertToCategoryDto_Stack(voucherCategory.getCategory()));
                }
            }
            voucherDto.setCategories(categoryDtos);
        }
        return voucherDto;
    }
}
