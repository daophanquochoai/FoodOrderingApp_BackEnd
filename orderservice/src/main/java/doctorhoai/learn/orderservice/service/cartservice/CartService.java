package doctorhoai.learn.orderservice.service.cartservice;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.CartDto;
import doctorhoai.learn.orderservice.dto.CartItemDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;

public interface CartService {

    void addCartItemIntoCart(CartItemDto cartItemDto, Integer userId);
    void removeCartItemFromCart(Integer cartItemId, Integer userId);
    PageObject getCartByUserId(Integer userId, Filter filter);
}
