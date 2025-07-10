package doctorhoai.learn.orderservice.service.cartservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.dto.CartDto;
import doctorhoai.learn.orderservice.dto.CartItemDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;
import doctorhoai.learn.orderservice.dto.userservice.UserDto;
import doctorhoai.learn.orderservice.dto.voucherservice.FoodDto;
import doctorhoai.learn.orderservice.exception.exception.CartItemNotFoundException;
import doctorhoai.learn.orderservice.exception.exception.CartNotFoundException;
import doctorhoai.learn.orderservice.exception.exception.FoodNotFoundException;
import doctorhoai.learn.orderservice.feign.foodservice.FoodFeign;
import doctorhoai.learn.orderservice.feign.userservice.UserFeign;
import doctorhoai.learn.orderservice.model.Cart;
import doctorhoai.learn.orderservice.model.CartItem;
import doctorhoai.learn.orderservice.repository.CartItemRepository;
import doctorhoai.learn.orderservice.repository.CartRepository;
import doctorhoai.learn.orderservice.utils.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserFeign userFeign;
    private final FoodFeign foodFeign;
    private final Mapper mapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public void addCartItemIntoCart(CartItemDto cartItemDto, Integer userId) {

        userFeign.getUserById(userId);

        Optional<Cart> cart = cartRepository.getCartByUserId(userId);

        //check food
        if( cartItemDto.getFoodId() == null || cartItemDto.getFoodId().getId() == null){
            throw new FoodNotFoundException();
        }

        foodFeign.getFood(cartItemDto.getFoodId().getId());

        CartItem cartItem = mapper.convertToCartItem(cartItemDto);
        cartItem.setFoodId(cartItemDto.getFoodId().getId());

        Cart cartNew;
        if( cart.isEmpty() ){
            cartNew = Cart.builder()
                    .userId(userId)
                    .isActive(true)
                    .build();
            cartNew.setCartItems(List.of(cartItem));
        }else{
            cartNew = cart.get();
            List<CartItem> cardItems = checkFoodWasAdd(cartNew.getCartItems(), cartItem, cartNew);
            cartNew.setCartItems(cardItems);
        }
        cartRepository.save(cartNew);
    }

    @Override
    public void removeCartItemFromCart(Integer cartItemId, Integer userId) {

        userFeign.getUserById(userId);

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(CartItemNotFoundException::new);

        if( !cartItem.getIsActive()){
            throw new CartItemNotFoundException();
        }

        cartItem.setIsActive(false);

        cartItemRepository.save(cartItem);
    }

    @Override
    public PageObject getCartByUserId(Integer userId, Filter filter) {

        //panigation
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }

        //get user
        ResponseEntity<ResponseObject> responseUser = userFeign.getUserById(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = new UserDto();
        if( responseUser.getStatusCode().is2xxSuccessful()){
            userDto = objectMapper.convertValue(responseUser.getBody().getData(), UserDto.class);
        }
        Page<CartItem> page = cartItemRepository.getCartItemByFilter(
                userId,
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay(),
                pageable
        );

        //get cart
        Cart cart = cartRepository.getCartByUserId(userId).orElseThrow(CartNotFoundException::new);
        CartDto cartDto = mapper.convertToCartDto(cart);
        cartDto.setUserId(userDto);

        PageObject pageObject = PageObject.builder()
                .page(filter.getPageNo())
                .totalPage(page.getTotalPages())
                .build();

        List<CartItem> cartItems = page.getContent();
        // if cart item empty
        if(cartItems.isEmpty()){
            pageObject.setData(cartDto);
            return pageObject;
        }
        // get info food in cart items
        List<Integer> idsFood = cartItems.stream().map(CartItem::getFoodId).filter(Objects::nonNull).distinct().toList();

        ResponseEntity<ResponseObject> responseFoods = foodFeign.getAllIdsFood(idsFood);
        List<FoodDto> foodDtos = new ArrayList<>();
        if( responseFoods.getStatusCode().is2xxSuccessful()){
            foodDtos = objectMapper.convertValue(
                    responseFoods.getBody().getData(),
                    new TypeReference<List<FoodDto>>() {}
            );
        }
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for( CartItem cartItem : cartItems ){
            CartItemDto cartItemDto = mapper.convertToCartItemDto(cartItem);
            if( cartItem.getFoodId() != null){
                cartItemDto.setFoodId(getFoodInList(foodDtos, cartItem.getFoodId()));
            }
            if( cartItem.getFoodId() != null){
                cartItemDtos.add(cartItemDto);
            }
        }
        cartDto.setCartItems(cartItemDtos);
        pageObject.setData(cartDto);
        return pageObject;
    }

    private FoodDto getFoodInList(List<FoodDto> foodDtos, Integer id){
        for( FoodDto foodDto : foodDtos){
            if( foodDto.getId().equals(id)){
                return foodDto;
            }
        }
        return null;
    }

    private List<CartItem> checkFoodWasAdd(List<CartItem> cartItems, CartItem cItem, Cart cart) {
        boolean flag = true;
        for( CartItem cartItem : cartItems){
            if( cartItem.getFoodId().equals(cItem.getFoodId()) && cartItem.getIsActive()){
                cartItem.setQuantity(cartItem.getQuantity() + cItem.getQuantity());
                cartItem.setPriceAtTime(cItem.getPriceAtTime());
                flag = false;
            }
        }
        if( flag ){
            cItem.setCartId(cart);
            cartItems.add(cItem);
        }
        return cartItems;
    }

}

