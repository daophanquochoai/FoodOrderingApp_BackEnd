package doctorhoai.learn.orderservice.service.orderservice;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.OrderDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    @Override
    public OrderDto save(OrderDto orderDto) {



        return null;
    }

    @Override
    public OrderDto update(OrderDto orderDto, Integer id) {
        return null;
    }

    @Override
    public PageObject getVoucherAll(Filter filter) {
        return null;
    }
}
