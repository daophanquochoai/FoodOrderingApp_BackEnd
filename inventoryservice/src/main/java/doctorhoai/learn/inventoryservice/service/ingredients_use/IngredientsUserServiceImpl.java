package doctorhoai.learn.inventoryservice.service.ingredients_use;

import doctorhoai.learn.inventoryservice.dto.orderservice.OrderItemDto;
import doctorhoai.learn.inventoryservice.exception.exception.OrderItemNotFoundException;
import doctorhoai.learn.inventoryservice.model.FoodIngredients;
import doctorhoai.learn.inventoryservice.model.HistoryIngredients;
import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import doctorhoai.learn.inventoryservice.repository.FoodIngredientsRepository;
import doctorhoai.learn.inventoryservice.repository.HistoryIngredientsRepository;
import doctorhoai.learn.inventoryservice.repository.IngredientsUserRepository;
import doctorhoai.learn.inventoryservice.utils.ExchangeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class IngredientsUserServiceImpl implements IngredientsUserService {

    private final FoodIngredientsRepository foodIngredientsRepository;
    private final HistoryIngredientsRepository historyIngredientsRepository;

    @Override
    public List<Map<String, Object>> afterBookFood(List<OrderItemDto> orderItemDtos) {
        //get ids food
        List<Integer> foodIds = orderItemDtos.stream().map(oItem -> {
            if( oItem.getFoodId() == null || oItem.getFoodId().getId() == null ){
                throw new OrderItemNotFoundException();
            }
            return oItem.getFoodId().getId();
        }).toList();

        // get ingredients
        List<FoodIngredients> foodIngredients = foodIngredientsRepository.getFoodIngredientOfFood( foodIds, List.of(true) );

        Map<Integer, Float> ingredientsQuantity = new HashMap<>();

        for (FoodIngredients foodIngredient : foodIngredients) {
            ingredientsQuantity.merge(
                    foodIngredient.getFoodId(),
                    ExchangeUnit.convertUnit(foodIngredient.getQuantityPerUnit(), foodIngredient.getIngredients().getUnit(), EUnitType.KG),
                    Float::sum
            );
        }

        List<HistoryIngredients> historyIngredients = historyIngredientsRepository.getIngredientsInInventory(
                ingredientsQuantity.keySet().stream().toList()
        );
        List<Map<String, Object>> valueReturn = new ArrayList<>();
        List<HistoryIngredients> listNeedUpdate = new ArrayList<>();
        for (HistoryIngredients historyIngredient : historyIngredients) {
            if( ingredientsQuantity.isEmpty()) break;
            if( ingredientsQuantity.get(historyIngredient.getIngredientsId().getId()) != null){

                Map<String, Object> needUse = new HashMap<>();
                if(  ingredientsQuantity.get(historyIngredient.getIngredientsId().getId()) > (historyIngredient.getQuantity() - historyIngredient.getUsedUnit()) ){
                    needUse.put("quantity", (historyIngredient.getQuantity() - historyIngredient.getUsedUnit()));
                    ingredientsQuantity.put(
                            historyIngredient.getIngredientsId().getId(),
                            ingredientsQuantity.get(historyIngredient.getIngredientsId().getId()) - (historyIngredient.getQuantity() - historyIngredient.getUsedUnit())
                    );
                    historyIngredient.setUsedUnit(historyIngredient.getQuantity());
                }else{
                    needUse.put("quantity",ingredientsQuantity.get(historyIngredient.getIngredientsId().getId()));
                    ingredientsQuantity.remove(historyIngredient.getIngredientsId().getId());
                    historyIngredient.setUsedUnit(historyIngredient.getUsedUnit() + ingredientsQuantity.get(historyIngredient.getIngredientsId().getId()));
                    listNeedUpdate.add(historyIngredient);
                }
                needUse.put("history", historyIngredient);
                valueReturn.add(needUse);
                listNeedUpdate.add(historyIngredient);
            }
        }
        historyIngredientsRepository.saveAll(listNeedUpdate);
        return valueReturn;
    }
}
