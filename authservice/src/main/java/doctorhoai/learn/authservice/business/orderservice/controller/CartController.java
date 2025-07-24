package doctorhoai.learn.authservice.business.orderservice.controller;

import doctorhoai.learn.authservice.business.orderservice.model.CartItemDto;
import doctorhoai.learn.authservice.business.orderservice.model.Filter;
import doctorhoai.learn.authservice.business.orderservice.service.cartservice.CartFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartFeign cartFeign;

    @PutMapping("/user/{username}/remove/{cartId}")
    ResponseEntity<ResponseObject> removeCart(
            @PathVariable String username,
            @PathVariable Integer cartId
    ){
        return cartFeign.removeCart(username, cartId);
    }

    @PostMapping("/user/{username}/all")
    public ResponseEntity<ResponseObject> getAllCarts(
            @PathVariable String username,
            @RequestBody Filter filter
    ){
        return cartFeign.getAllCarts(username, filter);
    }

    @PostMapping("/user/{username}/add")
    public ResponseEntity<ResponseObject> addCart(
            @PathVariable String username,
            @RequestBody @Valid CartItemDto cartItemDto
    ){
        return cartFeign.addCart(username, cartItemDto);
    }
}
