package doctorhoai.learn.inventoryservice.service.ingredients_use;

import doctorhoai.learn.inventoryservice.dto.IngredientsUseDto;
import doctorhoai.learn.inventoryservice.dto.orderservice.OrderItemDto;

import java.util.List;
import java.util.Map;

public interface IngredientsUserService {

    List<Map<String, Object>> afterBookFood(List<OrderItemDto> orderItemDtos);
    List<IngredientsUseDto> getIngredientsByOrderId(Integer orderId);
    void updateIngredientsUse(Integer orderId);
}
