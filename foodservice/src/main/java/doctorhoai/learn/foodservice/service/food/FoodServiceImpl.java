package doctorhoai.learn.foodservice.service.food;

import doctorhoai.learn.basedomain.kafka.EMessageType;
import doctorhoai.learn.basedomain.kafka.MessageTemplate;
import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.dto.FoodSizeDto;
import doctorhoai.learn.foodservice.dto.SizeDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.exception.CategoryNotFoundException;
import doctorhoai.learn.foodservice.exception.FoodNotFoundException;
import doctorhoai.learn.foodservice.exception.SizeNotFoundException;
import doctorhoai.learn.foodservice.kafka.proceducer.KafkaMessageSender;
import doctorhoai.learn.foodservice.model.Category;
import doctorhoai.learn.foodservice.model.Food;
import doctorhoai.learn.foodservice.model.FoodSize;
import doctorhoai.learn.foodservice.model.Size;
import doctorhoai.learn.foodservice.model.enums.EStatusCategory;
import doctorhoai.learn.foodservice.model.enums.EStatusFood;
import doctorhoai.learn.foodservice.repository.CategoryRepository;
import doctorhoai.learn.foodservice.repository.FoodRepository;
import doctorhoai.learn.foodservice.repository.SizeRepository;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;
    private final SizeRepository sizeRepository;
    private final KafkaMessageSender kafkaMessageSender;


    @Override
    public List<FoodDto> getFoodByIds(List<Integer> ids) {
        List<Food> foods = foodRepository.getFoodByIds(ids, null);
        return foods.stream().map(mapper::covertToFoodDto).toList();
    }

    @Override
    public PageObject getFoodByFilter(Filter filter) {

        Pageable pageable ;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }
        Page<Food> foods = foodRepository.getListFood(
                (filter.getId() == null || filter.getId().isEmpty()) ? null : filter.getId(),
                (filter.getStatusFoods() == null || filter.getStatusFoods().isEmpty()) ? null : filter.getStatusFoods(),
                filter.getSearch(),
                filter.getMinDiscount(),
                filter.getMaxDiscount(),
                filter.getMinPrice(),
                filter.getMaxPrice(),
                filter.getMinReady(),
                filter.getMaxReady(),
                (filter.getSizeIds() == null || filter.getSizeIds().isEmpty()) ? null : filter.getSizeIds(),
                filter.getCategoryId() == null || filter.getCategoryId().isEmpty() ? null : filter.getCategoryId(),
                pageable
        );
        PageObject pageObject = PageObject.builder()
                .totalPage((int) foods.getTotalElements())
                .page(filter.getPageNo())
                .build();
        if( filter.getDeep() == 0 ){
            pageObject.setData(foods.stream().map(mapper::covertToFoodDto).toList());
        }else {
            pageObject.setData(foods.stream().map( f -> {
                        FoodDto foodDto = mapper.covertToFoodDto(f);
                        if( f.getCategory() != null){
                            foodDto.setCategory(mapper.covertToCategoryDto(f.getCategory()));
                        }
                        if( f.getFoodSizes() != null && !f.getFoodSizes().isEmpty()){
                            foodDto.setFoodSizes(
                                    f.getFoodSizes().stream().map( fs -> {
                                        FoodSizeDto foodSizeDto = mapper.convertToFoodSizeDto(fs);
                                        if( fs.getSize() != null){
                                            foodSizeDto.setSizeId(mapper.convertToSizeDto(fs.getSize()));
                                        }
                                        return foodSizeDto;
                                    }).toList()
                            );
                        }
                        return foodDto;
                    }
            ).toList());
        }
        return pageObject;
    }

    @Override
    @Transactional
    public FoodDto createFood(FoodDto fooddto) {
        Food food = mapper.convertToFood(fooddto);
        if( fooddto.getCategory() != null){
            Category category = categoryRepository.findById(fooddto.getCategory().getId()).orElseThrow(CategoryNotFoundException::new);
            food.setCategory(category);
        }
        if( fooddto.getFoodSizes() != null && !fooddto.getFoodSizes().isEmpty()){
            List<FoodSize> foodSizes = new ArrayList<>();
            List<Integer> sizeIds = fooddto.getFoodSizes().stream().map(FoodSizeDto::getSizeId).map(SizeDto::getId).toList();
            List<Size> sizeList = sizeRepository.getSizeByIds(sizeIds);
            for( FoodSizeDto foodSizeDto : fooddto.getFoodSizes()){
                FoodSize foodSize = mapper.convertToFoodSize(foodSizeDto);
                if( foodSizeDto.getSizeId() != null ){
                    foodSize.setSize(getSizeByIndexList(sizeList, foodSizeDto.getSizeId().getId() ));
                }else{
                    throw new SizeNotFoundException();
                }
                foodSizes.add(foodSize);
                foodSize.setFood(food);
            }
            food.setFoodSizes(foodSizes);
        }
        food = foodRepository.save(food);
        FoodDto foodDto = convertListFood(food);
        kafkaMessageSender.sendEventToSearchAI(MessageTemplate.<FoodDto>builder().data(foodDto).messageType(EMessageType.CREATE_FOOD).build());
        return foodDto;
    }

    private Size getSizeByIndexList(List<Size> sizeList, Integer id){
        for( Size size : sizeList ){
            if( size != null ){
                if( id.equals(size.getId()) ){
                    return size;
                }
            }else{
                throw new SizeNotFoundException();
            }
        }
        throw new SizeNotFoundException();
    }

    @Override
    public FoodDto updateFood(FoodDto foodDto, Integer id) {
        Food food = foodRepository.findById(id).orElseThrow(FoodNotFoundException::new);
        food.setName(foodDto.getName());
        food.setDesc(foodDto.getDesc());
        food.setImage(foodDto.getImage());
        food.setStatus(foodDto.getStatus());
        if( foodDto.getCategory() != null){
            if( foodDto.getCategory().getId() != null && !foodDto.getCategory().getId().equals(foodDto.getId()) ){
                Category category = categoryRepository.findByIdAndStatus(food.getCategory().getId(), EStatusCategory.ACTIVE).orElseThrow(CategoryNotFoundException::new);
                food.setCategory(category);
            } else if( foodDto.getCategory().getId() == null){
                throw new CategoryNotFoundException();
            }
        }
        if( food.getFoodSizes() != null && !food.getFoodSizes().isEmpty() && foodDto.getFoodSizes() != null && !foodDto.getFoodSizes().isEmpty()){
            List<FoodSize> foodSizes = new ArrayList<>();
            for( FoodSize foodSize : food.getFoodSizes() ){
                if( foodSize.getId() != null ){
                    FoodSize foodUpdated = updateFoodSize(foodDto.getFoodSizes(), foodSize);
                    if( food.getStatus() == EStatusFood.DELETE || food.getStatus() == EStatusFood.OUT_STOCK){
                        foodUpdated.setIsActive(false);
                    }
                    foodSizes.add(foodUpdated);
                }else{
                    throw new FoodNotFoundException();
                }
            }
            food.setFoodSizes(foodSizes);
        }
        food = foodRepository.save(food);
        if( food.getStatus() == EStatusFood.DELETE || food.getStatus() == EStatusFood.OUT_STOCK){
            kafkaMessageSender.sendEventToSearchAI(MessageTemplate.<FoodDto>builder().data(foodDto).messageType(EMessageType.DELETE_FOOD).build());
        }else{
            kafkaMessageSender.sendEventToSearchAI(MessageTemplate.<FoodDto>builder().data(foodDto).messageType(EMessageType.UPDATE_FOOD).build());
        }
        return convertListFood(food);
    }

    private FoodSize updateFoodSize(List<FoodSizeDto> dto, FoodSize foodSize){
        for( FoodSizeDto foodSizeDto : dto){
            if( foodSizeDto.getId() == foodSize.getId()){
                foodSize.setDiscount(foodSizeDto.getDiscount());
                foodSize.setReadyInMinutes(foodSizeDto.getReadyInMinutes());
                foodSize.setIsActive(foodSizeDto.getIsActive());
                foodSize.setPrice(foodSizeDto.getPrice());
                return foodSize;
            }
        }
        throw new FoodNotFoundException();
    }

    @Override
    public FoodDto getFoodById(Integer id) {
        Food food = foodRepository.getFoodById(id).orElseThrow(FoodNotFoundException::new);
        if( food.getStatus() == EStatusFood.DELETE) {
            throw new FoodNotFoundException();
        }
        return convertListFood(food);
    }

    @Override
    public void checkFood(List<Integer> ids) {
        List<Food> foods = foodRepository.checkFood(ids, null);
        if( ids.size() != foods.size()){
            throw new FoodNotFoundException();
        }
    }

    @Override
    public List<FoodDto> getAllIdsFood(List<Integer> ids) {
        List<Food> foods = foodRepository.checkFood(ids, EStatusFood.ACTIVE);
        return foods.stream().map(this::convertListFood).toList();
    }

    @Override
    public List<FoodDto> getAll() {
        List<Food> foods = foodRepository.getAllFood( EStatusFood.ACTIVE);
        return foods.stream().map(this::convertListFood).toList();
    }

    private FoodDto convertListFood(Food food){
        FoodDto foodDto = mapper.covertToFoodDto(food);
        if( food.getFoodSizes() != null && !food.getFoodSizes().isEmpty() ){
            List<FoodSizeDto> foodSizesDto = new ArrayList<>();
            for( FoodSize foodSize : food.getFoodSizes()){
                FoodSizeDto dto = mapper.convertToFoodSizeDto(foodSize);
                if( foodSize.getSize() != null ){
                    dto.setSizeId(mapper.convertToSizeDto(foodSize.getSize()));
                }else{
                    throw new SizeNotFoundException();
                }
                foodSizesDto.add(dto);
            }
            foodDto.setFoodSizes(foodSizesDto);
        }
        return foodDto;
    }
}
