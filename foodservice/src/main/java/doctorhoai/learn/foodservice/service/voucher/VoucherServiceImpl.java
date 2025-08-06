package doctorhoai.learn.foodservice.service.voucher;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.CategoryDto;
import doctorhoai.learn.foodservice.dto.ExportVoucher;
import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.dto.VoucherDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.exception.*;
import doctorhoai.learn.foodservice.feign.userservice.UserFeign;
import doctorhoai.learn.foodservice.model.*;
import doctorhoai.learn.foodservice.model.enums.EStatusVoucher;
import doctorhoai.learn.foodservice.repository.*;
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
    private final VoucherFoodRepository voucherFoodRepository;
    private final VoucherCategoryRepository voucherCategoryRepository;
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

        List<VoucherDto> voucherDtos = vouchers.getContent().stream().map( i -> {
            VoucherDto voucherDto = mapper.convertToVoucherDto(i);
            if( i.getVoucherFoods() != null && !i.getVoucherFoods().isEmpty()){
                voucherDto.setFoods(convertListVoucherFood_ToFood(i.getVoucherFoods()));
            }
            if( i.getVoucherCategories() != null && !i.getVoucherCategories().isEmpty()){
                voucherDto.setCategories(convertListVoucherCategory_ToCategory(i.getVoucherCategories()));
            }
            return voucherDto;
        }).toList();

        return PageObject.builder()
                .page(filter.getPageNo())
                .totalPage(vouchers.getTotalPages())
                .data(voucherDtos)
                .build();
    }

    public List<FoodDto> convertListVoucherFood_ToFood( List<VoucherFood> voucherFoods){
        List<FoodDto> returnValue = new ArrayList<>();
        for( VoucherFood voucherFood : voucherFoods ){
            returnValue.add(
                    mapper.covertToFoodDto(voucherFood.getFood())
            );
        }
        return returnValue;
    }

    public List<CategoryDto> convertListVoucherCategory_ToCategory( List<VoucherCategory> voucherCategories){
        List<CategoryDto> returnValue = new ArrayList<>();
        for( VoucherCategory voucherCategory : voucherCategories ){
            returnValue.add(
                    mapper.covertToCategoryDto(voucherCategory.getCategory())
            );
        }
        return returnValue;
    }

//    public CategoryDto getCategory(List<CategoryDto> list, Integer id){
//        for( CategoryDto categoryDto : list){
//            if( categoryDto.getId() == id ){
//                return categoryDto;
//            }
//        }
//        return  null;
//    }
//
//    public FoodDto getFoodInList(List<FoodDto> list, Integer id){
//        for( FoodDto dto : list){
//            if( dto.getId() == id){
//                return dto;
//            }
//        }
//        return null;
//    }

    @Override
    @Transactional
    public VoucherDto createVoucher(VoucherDto voucherDto) {
        Voucher voucher = mapper.convertToVoucher(voucherDto);

        Optional<Voucher> voucherFind = voucherRepository.findVoucherByCode(voucher.getCode());
        if( voucherFind.isPresent()){
            throw new VoucherDuplicate();
        }
//        // Gắn foods nếu có
//        List<FoodDto> foodDtoList = voucherDto.getFoods();
//        if (foodDtoList != null && !foodDtoList.isEmpty()) {
//            List<Integer> foodIds = foodDtoList.stream().map(FoodDto::getId).toList();
//            List<Food> foods = foodRepository.getListFoodByListId(foodIds);
//
//            if (foods.size() != foodIds.size()) {
//                throw new FoodNotFoundException();
//            }
//
//            List<VoucherFood> voucherFoods = new ArrayList<>();
//            for (Food food : foods) {
//                VoucherFood voucherFood = VoucherFood.builder()
//                        .voucher(voucher)
//                        .food(food)
//                        .isActive(true)
//                        .build();
//                voucherFoods.add(voucherFood);
//            }
//
//            voucher.setVoucherFoods(voucherFoods);
//        }
//
//        // Gắn categories nếu có
//        List<CategoryDto> categoryDtoList = voucherDto.getCategories();
//        if (categoryDtoList != null && !categoryDtoList.isEmpty()) {
//            List<Integer> categoryIds = categoryDtoList.stream().map(CategoryDto::getId).toList();
//            List<Category> categories = categoryRepository.getListCategoryByListId(categoryIds);
//
//            if (categories.size() != categoryIds.size()) {
//                throw new CategoryNotFoundException();
//            }
//
//            List<VoucherCategory> voucherCategories = new ArrayList<>();
//            for (Category category : categories) {
//                VoucherCategory voucherCategory = VoucherCategory.builder()
//                        .voucher(voucher)
//                        .category(category)
//                        .isActive(true)
//                        .build();
//                voucherCategories.add(voucherCategory);
//            }
//
//            voucher.setVoucherCategories(voucherCategories);
//        }

        voucher.setUsedCount(0);
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
        if( data.getMaxUse() > data.getUsedCount() ){
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
        if( data.getUsedCount() - 1 >= 0){
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

    @Override
    public void updateVoucher(VoucherDto voucherDto, Integer id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(VoucherNotFoundException::new);
        voucher.setMaxDiscount(voucherDto.getMaxDiscount());
        voucher.setCode(voucherDto.getCode());
        voucher.setDiscountType(voucherDto.getDiscountType());
        voucher.setDesc(voucherDto.getDesc());
        voucher.setDiscountValue(voucherDto.getDiscountValue());
        voucher.setMaxUse(voucherDto.getMaxUse());
        voucher.setStartDate(voucherDto.getStartDate());
        voucher.setEndDate(voucherDto.getEndDate());
        voucher.setStatus(voucherDto.getStatus());
        voucherRepository.save(voucher);
    }

    @Override
    @Transactional
    public void exportVoucher(ExportVoucher exportVoucher) {
        Voucher voucher = voucherRepository.findById(exportVoucher.getId()).orElseThrow(VoucherNotFoundException::new);
        voucher.setStatus(EStatusVoucher.ACTIVE);
        if( exportVoucher.getCategoryOld() != null && exportVoucher.getCategoryOld().length > 0){
            List<VoucherCategory> voucherCategorieOld = voucherCategoryRepository.getVoucherCategoriesByCategoryIds(exportVoucher.getCategoryOld());
            voucherCategorieOld.stream().peek(i -> i.setIsActive(false));
            voucherCategoryRepository.saveAll(voucherCategorieOld);
        }
        if( exportVoucher.getFoodOld() != null && exportVoucher.getFoodOld().length > 0){
            List<VoucherFood> voucherFoodOld = voucherFoodRepository.getVoucherFoodByFoodIds(exportVoucher.getFoodOld());

            // update
            voucherFoodOld.stream().peek(i -> i.setIsActive(false));

            voucherFoodRepository.saveAll(voucherFoodOld);
        }
        if( exportVoucher.getFoodNew() != null && exportVoucher.getFoodNew().length > 0){
            List<VoucherFood> voucherFoods = new ArrayList<>();
            for( Integer id : exportVoucher.getFoodNew()){
                Food food = Food.builder()
                        .id(id)
                        .build();
                VoucherFood voucherFood = VoucherFood.builder()
                        .voucher(voucher)
                        .food(food)
                        .isActive(true)
                        .build();
                voucherFoods.add(voucherFood);
            }
            voucherFoodRepository.saveAll(voucherFoods);
        }
        if( exportVoucher.getCategoryNew() != null && exportVoucher.getCategoryNew().length > 0){
            List<VoucherCategory> voucherCategories = new ArrayList<>();
            for( Integer id : exportVoucher.getCategoryNew()){
                Category category = Category.builder()
                        .id(id)
                        .build();
                VoucherCategory voucherCategory = VoucherCategory.builder()
                        .voucher(voucher)
                        .category(category)
                        .isActive(true)
                        .build();
                voucherCategories.add(voucherCategory);
            }
            voucherCategoryRepository.saveAll(voucherCategories);
        }
        voucherRepository.save(voucher);
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
