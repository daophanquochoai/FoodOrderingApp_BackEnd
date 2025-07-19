package doctorhoai.learn.orderservice.service.orderservice;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.OrderDto;
import doctorhoai.learn.orderservice.dto.OrderItemDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;

import java.util.List;

public interface OrderService {
    OrderDto save(OrderDto orderDto, List<OrderItemDto> orderItemDtoList);
    OrderDto update(OrderDto orderDto, Integer id);
    PageObject getVoucherAll(Filter filter);
}
