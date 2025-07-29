package doctorhoai.learn.orderservice.service.cartservice;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.CartItemDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;

public interface CartService {

    void addCartItemIntoCart(CartItemDto cartItemDto, String username);
    void removeCartItemFromCart(Integer cartItemId, String name);
    PageObject getCartByUsername(String username, Filter filter);
    void createCart(Integer userId);
}
