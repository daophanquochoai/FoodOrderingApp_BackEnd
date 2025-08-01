package doctorhoai.learn.orderservice.service.orderservice;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.OrderDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;

public interface OrderService {
    OrderDto save(OrderDto orderDto);
    OrderDto update(OrderDto orderDto, Integer id);
    PageObject getOrderByFilter(Filter filter);
}
