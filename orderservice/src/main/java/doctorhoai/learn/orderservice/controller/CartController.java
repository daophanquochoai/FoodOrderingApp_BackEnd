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

    @PostMapping("/user/{id}/add")
    public ResponseEntity<ResponseObject> addCart(
            @PathVariable Integer id,
            @RequestBody @Valid CartItemDto cartItemDto
            )
    {
        cartService.addCartItemIntoCart(cartItemDto, id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_CART_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @PutMapping("/user/{id}/remove/{cartId}")
    public ResponseEntity<ResponseObject> removeCart(
            @PathVariable Integer id,
            @PathVariable Integer cartId
    ){
        cartService.removeCartItemFromCart(cartId, id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_CART_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @GetMapping("/user/{id}/all")
    public ResponseEntity<ResponseObject> getAllCarts(
            @PathVariable Integer id,
            @RequestBody Filter filter
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_CART_SUCCESSFUL.getMessage())
                        .data(cartService.getCartByUserId(id, filter))
                        .build()
        );
    }
}
