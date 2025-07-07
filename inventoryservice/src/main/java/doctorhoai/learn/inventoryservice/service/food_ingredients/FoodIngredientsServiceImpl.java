package doctorhoai.learn.inventoryservice.service.food_ingredients;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.dto.FoodIngredientsDto;
import doctorhoai.learn.inventoryservice.exception.contanst.EMessageException;
import doctorhoai.learn.inventoryservice.exception.exception.FoodNotFoundException;
import doctorhoai.learn.inventoryservice.exception.exception.IngredientsNotFoundException;
import doctorhoai.learn.inventoryservice.feign.foodservice.FoodFeign;
import doctorhoai.learn.inventoryservice.mapper.Mapper;
import doctorhoai.learn.inventoryservice.model.FoodIngredients;
import doctorhoai.learn.inventoryservice.model.Ingredients;
import doctorhoai.learn.inventoryservice.repository.FoodIngredientsRepository;
import doctorhoai.learn.inventoryservice.repository.IngredientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FoodIngredientsServiceImpl implements FoodIngredientsService {

    private final FoodIngredientsRepository foodIngredientsRepository;
    private final IngredientsRepository ingredientsRepository;
    private final FoodFeign foodFeign;
    private final Mapper mapper;


    @Override
    public List<FoodIngredientsDto> getFoodIngredientsDtoByFoodId(Integer foodId) {
        //get food (check)
        ResponseEntity<ResponseObject> responseFood = foodFeign.getFood(foodId);
       if (!responseFood.getStatusCode().is2xxSuccessful() || responseFood.getBody() == null) {
           throw new RuntimeException(EMessageException.SERVICE_DOWN.getMessage());
       }

        //get food ingredients
        List<FoodIngredients> foodIngredients = foodIngredientsRepository.getFoodIngredientsByFoodId(foodId);

        return convertToFoodIngredientsDto(foodIngredients);
    }

    @Override
    @Transactional
    public void createFoodIngredients(List<FoodIngredientsDto> foodIngredientsDtos, Integer id) {
        //get null
        for( FoodIngredientsDto foodIngredientsDto : foodIngredientsDtos){
            if( foodIngredientsDto.getFoodId() == null){
                throw new FoodNotFoundException();
            }
            if( foodIngredientsDto.getIngredients() == null || foodIngredientsDto.getIngredients().getId() == null){
                throw new IngredientsNotFoundException();
            }
        }

        // check food
        List<Integer> idsFood = List.of(id);
        foodFeign.checkFood(idsFood);

        // check ingredients
        List<Integer> idsIngredients = foodIngredientsDtos.stream().map(i -> i.getIngredients().getId()).distinct().toList();
        List<Ingredients> ingredients = ingredientsRepository.getFoodIngredientsByIds(idsIngredients);


        List<FoodIngredients> foodIngredients = convertToFoodIngredients(foodIngredientsDtos,ingredients);
        foodIngredientsRepository.saveAll(foodIngredients);
    }

    @Override
    @Transactional
    public void updateFoodIngredients(List<FoodIngredientsDto> foodIngredientsDto, Integer foodId) {
        List<Integer> ids = foodIngredientsDto.stream().map(FoodIngredientsDto::getFoodId).filter(Objects::nonNull).distinct().toList();

        List<FoodIngredients> foodIngredients = foodIngredientsRepository.getFoodIngredientsByFoodId(foodId);
        List<Integer> idsRepo = foodIngredients.stream().map(i -> {
            if( i.getIngredients() == null || i.getIngredients().getId() == null){
                return null;
            }
            return i.getIngredients().getId();
        }).filter(Objects::nonNull).distinct().toList();

        //add new
        List<FoodIngredientsDto> fIngredientsAdd = foodIngredientsDto.stream().filter( i -> {
            if( i.getIngredients() == null || i.getIngredients().getId() == null ||  !idsRepo.contains(i.getIngredients().getId())){
                return true;
            }
            return false;
        }).toList();

        //remove
        List<FoodIngredients> fIngredientsUpdate = foodIngredients.stream().filter( i -> {
            if( i.getIngredients() == null || i.getIngredients().getId() == null){
                return false;
            }
            return !ids.contains(i.getIngredients().getId());
        }).peek(i -> i.setIsActive(false)).toList();

        //update
        List<FoodIngredients> fInfredientsNeedUpdate = new ArrayList<>();
        for( FoodIngredients f : foodIngredients){
            if( ids.contains(f.getId())){
                FoodIngredients fUpdate = f;
                fUpdate.setQuantityPerUnit(foodIngredientsDto.stream().filter(i -> i.getId() == f.getId()).findFirst().get().getQuantityPerUnit());
                fIngredientsUpdate.add(fUpdate);
            }
        } // TODO : Chua cap nhat lai du lieu cu

        if(!fIngredientsAdd.isEmpty()){
            createFoodIngredients(fIngredientsAdd,foodId);
        }
        foodIngredientsRepository.saveAll(fIngredientsUpdate);
        foodIngredientsRepository.saveAll(fInfredientsNeedUpdate);
    }

    private List<FoodIngredientsDto> convertToFoodIngredientsDto(List<FoodIngredients> foodIngredients) {
        List<FoodIngredientsDto> foodIngredientsDtos = new ArrayList<>();
        for (FoodIngredients foodIngredient : foodIngredients) {
            FoodIngredientsDto foodIngredientsDto = mapper.convertToFoodIngredientsDto(foodIngredient);
            if (foodIngredient.getIngredients() == null) {
                throw new IngredientsNotFoundException();
            }
            if(!foodIngredient.getIngredients().getIsActive()){ continue;}
            foodIngredientsDto.setIngredients(mapper.convertToIngredientsDto(foodIngredient.getIngredients()));
            foodIngredientsDtos.add(foodIngredientsDto);
        }
        return foodIngredientsDtos;
    }

    private List<FoodIngredients> convertToFoodIngredients(List<FoodIngredientsDto> foodIngredientsDtos, List<Ingredients> ingredients) {
        List<FoodIngredients> foodIngredients = new ArrayList<>();
        for (FoodIngredientsDto foodIngredientsDto : foodIngredientsDtos) {
            FoodIngredients foodIngredient = mapper.convertToFoodIngredients(foodIngredientsDto);
            foodIngredient.setIngredients(getIngredientsById(foodIngredientsDto.getIngredients().getId(), ingredients));
            foodIngredients.add(foodIngredient);
        }
        return foodIngredients;
    }

    private Ingredients getIngredientsById(Integer id, List<Ingredients> ingredients) {
        for( Ingredients in : ingredients){
            if(in.getId().equals(id)){
                return in;
            }
        }
        throw new IngredientsNotFoundException();
    }
}
