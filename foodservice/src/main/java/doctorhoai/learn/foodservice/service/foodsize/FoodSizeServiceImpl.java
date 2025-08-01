package doctorhoai.learn.foodservice.service.foodsize;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.dto.FoodSizeDto;
import doctorhoai.learn.foodservice.dto.SizeDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.exception.FoodNotFoundException;
import doctorhoai.learn.foodservice.exception.FoodSizeException;
import doctorhoai.learn.foodservice.exception.SizeNotFoundException;
import doctorhoai.learn.foodservice.model.Food;
import doctorhoai.learn.foodservice.model.FoodSize;
import doctorhoai.learn.foodservice.model.Size;
import doctorhoai.learn.foodservice.repository.FoodRepository;
import doctorhoai.learn.foodservice.repository.FoodSizeRepository;
import doctorhoai.learn.foodservice.repository.SizeRepository;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodSizeServiceImpl implements FoodSizeService {

    private final FoodSizeRepository foodSizeRepository;
    private final FoodRepository foodRepository;
    private final SizeRepository sizeRepository;
    private final Mapper mapper;


    @Override
    public FoodSizeDto createNewFoodSize(FoodSizeDto foodSizeDto) {
        if( foodSizeDto.getSizeId() == null){
            throw new SizeNotFoundException();
        }
        if( foodSizeDto.getFoodId() == null){
            throw new FoodNotFoundException();
        }

        Food food = foodRepository.getFoodById(foodSizeDto.getFoodId().getId()).orElseThrow(FoodNotFoundException::new);
        Size size = sizeRepository.getSizeById(foodSizeDto.getSizeId().getId()).orElseThrow(SizeNotFoundException::new);

        FoodSize foodSize = mapper.convertToFoodSize(foodSizeDto);
        foodSize.setFood(food);
        foodSize.setSize(size);
        foodSize = foodSizeRepository.save(foodSize);
        FoodSizeDto foodSizeReturn = mapper.convertToFoodSizeDto(foodSize);
        foodSizeReturn.setFoodId(mapper.covertToFoodDto(food));
        foodSizeReturn.setSizeId(mapper.convertToSizeDto(size));
        return foodSizeReturn;
    }

    @Override
    public FoodSizeDto updateFoodSize(FoodSizeDto foodSizeDto, Integer id) {

        FoodSize foodSize = foodSizeRepository.getFoodSizeByIdAndIsActive(id, true).orElseThrow(FoodNotFoundException::new);

        foodSize.setDiscount(foodSizeDto.getDiscount());
        foodSize.setReadyInMinutes(foodSizeDto.getReadyInMinutes());
        foodSize.setPrice(foodSizeDto.getPrice());
        foodSize.setIsActive(foodSizeDto.getIsActive());

        if( foodSizeDto.getFoodId() != null){
            if( foodSize.getFood() == null || foodSize.getFood().getId() != foodSizeDto.getFoodId().getId()){
                Food food = foodRepository.getFoodById(foodSizeDto.getFoodId().getId()).orElseThrow(FoodNotFoundException::new);
                foodSize.setFood(food);
            }
        }

        if( foodSizeDto.getSizeId() != null){
            if( foodSize.getSize() == null || foodSize.getSize().getId() != foodSizeDto.getSizeId().getId()){
                Size size = sizeRepository.getSizeByIdAndIsActive(foodSizeDto.getSizeId().getId(), true).orElseThrow(SizeNotFoundException::new);
                foodSize.setSize(size);
            }
        }

        foodSize = foodSizeRepository.save(foodSize);
        FoodSizeDto foodSizeReturn =  mapper.convertToFoodSizeDto(foodSize);
        foodSizeReturn.setFoodId(mapper.covertToFoodDto(foodSize.getFood()));
        foodSizeReturn.setSizeId(mapper.convertToSizeDto(foodSize.getSize()));
        return foodSizeReturn;
    }

    @Override
    public PageObject getAllFoodSize(Filter filter) {
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }

        Page<FoodSize> foodSizePage = foodSizeRepository.getFoodSizeByFilter(
                filter.getSearch(),
                filter.getMinDiscount(),
                filter.getMaxDiscount(),
                filter.getMinPrice(),
                filter.getMaxPrice(),
                filter.getMinReady(),
                filter.getMaxReady(),
                filter.getIsActive(),
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay(),
                filter.getFoodIds(),
                filter.getSizeIds(),
                pageable
        );

        PageObject pageObject = PageObject.builder()
                .page(filter.getPageNo())
                .totalPage(foodSizePage.getTotalPages())
                .build();

        List<FoodSizeDto> foodSizeDtoList = convertListFoodSize(foodSizePage.getContent());
        pageObject.setData(foodSizeDtoList);
        return pageObject;
    }

    @Override
    public FoodSizeDto getFoodSizeById(Integer id) {
        Optional<FoodSize> foodSizeOptional = foodSizeRepository.getFoodSizeByIdAndIsActive(id, true);
        if(foodSizeOptional.isEmpty()){
            throw new FoodSizeException();
        }
        FoodSize fs = foodSizeOptional.get();
        return convertToFoodSizeDto(fs);
    }

    @Override
    public List<FoodSizeDto> getFoodSizeList(List<Integer> idsFood) {
        List<FoodSize> foodSizes = foodSizeRepository.getFoodSizeByIdsAndIsActive(idsFood, null); // can check neu order
        return convertListFoodSize(foodSizes);
    }

    private List<FoodSizeDto> convertListFoodSize(List<FoodSize> foodSizes){
        List<FoodSizeDto> foodSizeDtos = new ArrayList<>();
        for( FoodSize fs : foodSizes){
            FoodSizeDto foodSizeDto = convertToFoodSizeDto(fs);
            if( fs.getFood() != null && fs.getFood().getCategory() != null){
                foodSizeDto.getFoodId().setCategory(mapper.covertToCategoryDto(fs.getFood().getCategory()));
            }
            foodSizeDtos.add(foodSizeDto);
        }
        return foodSizeDtos;
    }

    public FoodSizeDto convertToFoodSizeDto(FoodSize fs){
        FoodSizeDto foodSizeDto = mapper.convertToFoodSizeDto(fs);
        if( fs.getFood() != null && fs.getFood().getId() != null){
            FoodDto foodDto = mapper.covertToFoodDto(fs.getFood());
            foodSizeDto.setFoodId(foodDto);
        }
        if( fs.getSize() != null && fs.getSize().getId() != null){
            SizeDto sizeDto = mapper.convertToSizeDto(fs.getSize());
            foodSizeDto.setSizeId(sizeDto);
        }
        return foodSizeDto;
    }
}
