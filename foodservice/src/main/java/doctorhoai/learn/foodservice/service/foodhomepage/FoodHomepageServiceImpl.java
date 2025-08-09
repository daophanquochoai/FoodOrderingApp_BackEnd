package doctorhoai.learn.foodservice.service.foodhomepage;

import doctorhoai.learn.foodservice.dto.FoodHomepageDto;
import doctorhoai.learn.foodservice.exception.FoodHomepageNotFoundException;
import doctorhoai.learn.foodservice.exception.FoodNotFoundException;
import doctorhoai.learn.foodservice.model.Food;
import doctorhoai.learn.foodservice.model.FoodHomepage;
import doctorhoai.learn.foodservice.repository.FoodHomepageRepository;
import doctorhoai.learn.foodservice.repository.FoodRepository;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodHomepageServiceImpl implements FoodHomepageService {
    private final FoodHomepageRepository foodHomepageRepository;
    private final FoodRepository foodRepository;
    private final Mapper mapper;

    @Override
    public List<FoodHomepageDto> getLatestProductFoods() {
        List<FoodHomepage> foodHomepages = foodHomepageRepository.findByIsActiveTrueAndFeature("latest-product");
        return foodHomepages.stream()
                .map(mapper::covertToFoodHomepageDto)
                .toList();
    }

    @Override
    public List<FoodHomepageDto> getDealOfTheWeekFoods() {
        List<FoodHomepage> foodHomepages = foodHomepageRepository.findByIsActiveTrueAndFeature("deal-of-the-week");
        return foodHomepages.stream()
                .map(mapper::covertToFoodHomepageDto)
                .toList();
    }

    @Override
    public FoodHomepageDto addFoodHomepage(FoodHomepageDto foodHomepageDto) {
        if (foodHomepageDto.getFood() == null || foodHomepageDto.getFood().getId() == null) {
            throw new IllegalArgumentException("Food id must not be null");
        }

        Integer foodId = foodHomepageDto.getFood().getId();
        String feature = foodHomepageDto.getFeature();

        Optional<FoodHomepage> homepageOpt = foodHomepageRepository.findByFeatureAndFood_Id(feature, foodId);

        FoodHomepage foodHomepage;

        if (homepageOpt.isPresent()) {
            foodHomepage = homepageOpt.get();
            foodHomepage.setIsActive(true);
        } else {
            foodHomepage = mapper.convertToFoodHomepage(foodHomepageDto);
            Food food = foodRepository.findById(foodHomepageDto.getFood().getId()).orElseThrow(FoodNotFoundException::new);
            foodHomepage.setFood(food);
            foodHomepage.setIsActive(true);
        }
        foodHomepage = foodHomepageRepository.save(foodHomepage);
        return mapper.covertToFoodHomepageDto(foodHomepage);
    }

    @Override
    public FoodHomepageDto deleteFoodHomepage(Integer id) {
        FoodHomepage foodHomepage = foodHomepageRepository.findById(id)
                .orElseThrow(FoodHomepageNotFoundException::new);
        foodHomepage.setIsActive(false);
        foodHomepage = foodHomepageRepository.save(foodHomepage);
        return mapper.covertToFoodHomepageDto(foodHomepage);
    }
}
