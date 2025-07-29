package doctorhoai.learn.orderservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.dto.CartItemDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;
import doctorhoai.learn.orderservice.service.cartservice.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/user/{username}/add")
    public ResponseEntity<ResponseObject> addCart(
            @PathVariable String username,
            @RequestBody @Valid CartItemDto cartItemDto
            )
    {
        cartService.addCartItemIntoCart(cartItemDto, username);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_CART_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @PutMapping("/user/{username}/remove/{cartId}")
    public ResponseEntity<ResponseObject> removeCart(
            @PathVariable String username,
            @PathVariable Integer cartId
    ){
        cartService.removeCartItemFromCart(cartId, username);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_CART_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @PostMapping("/user/{username}/all")
    public ResponseEntity<ResponseObject> getAllCarts(
            @PathVariable String username,
            @RequestBody Filter filter
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_CART_SUCCESSFUL.getMessage())
                        .data(cartService.getCartByUsername(username, filter))
                        .build()
        );
    }

    @PostMapping("/cart/{userId}")
    public ResponseEntity<ResponseObject> createCart(
            @PathVariable Integer userId
    ){
        cartService.createCart(userId);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_CART_SUCCESSFUL.getMessage())
                        .build()
        );
    }
}
