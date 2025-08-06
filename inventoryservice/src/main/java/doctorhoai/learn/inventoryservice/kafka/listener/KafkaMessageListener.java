package doctorhoai.learn.inventoryservice.kafka.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.basedomain.kafka.order.EStatusOrder;
import doctorhoai.learn.basedomain.kafka.order.EventOrder;
import doctorhoai.learn.inventoryservice.exception.exception.IngredientsNotFoundException;
import doctorhoai.learn.inventoryservice.kafka.model.OrderDto;
import doctorhoai.learn.inventoryservice.kafka.model.OrderItemDto;
import doctorhoai.learn.inventoryservice.kafka.sender.KafkaMessageSender;
import doctorhoai.learn.inventoryservice.model.FoodIngredients;
import doctorhoai.learn.inventoryservice.model.HistoryIngredients;
import doctorhoai.learn.inventoryservice.model.IngredientsUse;
import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import doctorhoai.learn.inventoryservice.repository.FoodIngredientsRepository;
import doctorhoai.learn.inventoryservice.repository.HistoryIngredientsRepository;
import doctorhoai.learn.inventoryservice.repository.IngredientsUseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final ObjectMapper objectMapper;
    private final FoodIngredientsRepository foodIngredientsRepository;
    private final HistoryIngredientsRepository historyIngredientsRepository;
    private final IngredientsUseRepository ingredientsUseRepository;
    private final KafkaMessageSender kafkaMessageSender;
    @Value("${spring.kafka.topic.order}")
    private String orderTopic;
    @Value("${spring.kafka.topic.sender_rollback}")
    private String senderRollbackTopic;

    @RetryableTopic(attempts = "4", backoff = @Backoff( delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "inventory", groupId = "inventory")
    @Transactional
    public void receiveOrder(EventOrder<OrderDto> message){
        try{
            System.out.println("Receving...");

            EventOrder<OrderDto> eventOrder = objectMapper.convertValue(
                    message,
                    new TypeReference<EventOrder<OrderDto>>() {}
            );

            List<OrderItemDto> orderItems = objectMapper.convertValue(
                    eventOrder.getOrder().getOrderItems(),
                    new TypeReference<List<OrderItemDto>>() {}
            );

            List<Integer> foodSizeIds = new ArrayList<>();
            orderItems.stream().map(item -> item.getFoodId().getId()).toList();
            Map<Integer, Float> ingredientsForFood = new HashMap<>();
            Map<Integer, Integer> ingredientsOrderItems = new HashMap<>();

            for( OrderItemDto orderItemDto : orderItems ){
                foodSizeIds.add(orderItemDto.getFoodId().getId());
                ingredientsOrderItems.put(orderItemDto.getFoodId().getId(), orderItemDto.getId());
            }

            List<FoodIngredients> foodIngredients = foodIngredientsRepository.getFoodIngredientOfFood(foodSizeIds, List.of(true));
            if( foodIngredients.isEmpty()){
                throw new IngredientsNotFoundException();
            }
            for (FoodIngredients ingredient : foodIngredients) {
                if( ingredient.getIngredients() != null ){
                    Integer id = ingredient.getIngredients().getId();
                    Float quantity = ingredient.getQuantityPerUnit();

                    ingredientsForFood.merge(id, quantity, Float::sum);
                }else{
                    throw new IngredientsNotFoundException();
                }
            }

            List<HistoryIngredients> historyIngredients = historyIngredientsRepository.getIngredientsInInventory(ingredientsForFood.keySet().stream().toList());
            List<IngredientsUse> ingredientsUses = new ArrayList<>();

            List<HistoryIngredients> historyIngredientsNeedUpdate = new ArrayList<>();
            for(HistoryIngredients historyIngredient : historyIngredients){
                Integer ingredientId = historyIngredient.getIngredientsId().getId();
                if( ingredientsForFood.get(ingredientId) != null ){
                    // tong nghuyen lieu
                    Float availableQuantity = historyIngredient.getQuantity();
                    // nguyen lieu da su dung
                    Float usedQuantity = historyIngredient.getUsedUnit();
                    if( historyIngredient.getUses() != null && !historyIngredient.getUses().isEmpty()){
                        for (IngredientsUse ingredientsUse : historyIngredient.getUses()) {
                            if( !ingredientsUse.getIsActive()){
                                continue;
                            }
                            usedQuantity += ingredientsUse.getQuantity();
                        }
                    }

                    if( ingredientsForFood.get(ingredientId) + usedQuantity >= availableQuantity ){
                        IngredientsUse ingredientsUse = IngredientsUse.builder()
                                .historyIngredients(historyIngredient)
                                .unit(EUnitType.KG)
                                .quantity(availableQuantity - usedQuantity)
                                .orderId(eventOrder.getOrder().getId())
                                .isActive(true)
                                .build();
                        ingredientsUses.add(ingredientsUse);
                        historyIngredient.setUsedUnit(availableQuantity);
                        historyIngredientsNeedUpdate.add(historyIngredient);

                        ingredientsForFood.put(ingredientId, ingredientsForFood.get(ingredientId) - availableQuantity - usedQuantity);
                        if( ingredientsForFood.get(ingredientId) == 0 ){
                            ingredientsForFood.remove(ingredientId);
                        }
                    }else{
                        IngredientsUse ingredientsUse = IngredientsUse.builder()
                                .historyIngredients(historyIngredient)
                                .unit(EUnitType.KG)
                                .quantity(ingredientsForFood.get(ingredientId))
                                .orderId(eventOrder.getOrder().getId())
                                .isActive(true)
                                .build();
                        ingredientsUses.add(ingredientsUse);
                        historyIngredient.setUsedUnit(ingredientsForFood.get(ingredientId) + usedQuantity);
                        ingredientsForFood.remove(ingredientId);
                        historyIngredientsNeedUpdate.add(historyIngredient);
                    }
                    if(ingredientsForFood.keySet().isEmpty()){
                        break;
                    }
                }else{
                    throw new IngredientsNotFoundException();
                }
            }

            if( !ingredientsForFood.keySet().isEmpty() ){
                throw new IngredientsNotFoundException();
            }
            ingredientsUseRepository.saveAll(ingredientsUses);
            historyIngredientsRepository.saveAll(historyIngredientsNeedUpdate);
            System.out.println("Send to order ...");
            kafkaMessageSender.sendTo(message, orderTopic);
        }catch( Exception ex ){
            System.out.println("Rollback ...");
            message.setMessage(ex.getMessage());
            message.setStatus(EStatusOrder.ROLL_BACK);
            kafkaMessageSender.sendTo(message, senderRollbackTopic);
        }
    }

}
