package doctorhoai.learn.orderservice.service.orderservice;

import com.stripe.exception.StripeException;
import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.OrderDto;
import doctorhoai.learn.orderservice.dto.UpdateStatusOrder;
import doctorhoai.learn.orderservice.dto.filter.Filter;
import doctorhoai.learn.orderservice.model.enums.EStatusOrder;

import java.util.Map;

public interface OrderService {
    OrderDto save(OrderDto orderDto) throws StripeException;
    OrderDto update(OrderDto orderDto, Integer id);
    PageObject getOrderByFilter(Filter filter);
    void updateStatusOrder(Integer orderId, UpdateStatusOrder statusOrder);
}
