package doctorhoai.learn.authservice.business.orderservice.service.cartservice;

import doctorhoai.learn.authservice.business.orderservice.model.CartItemDto;
import doctorhoai.learn.authservice.business.orderservice.model.Filter;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "orderservice",
        path = "/cart",
        contextId = "cartBusiness",
        fallbackFactory = CartFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface CartFeign {

    @PutMapping("/user/{username}/remove/{cartId}")
    ResponseEntity<ResponseObject> removeCart(
            @PathVariable String username,
            @PathVariable Integer cartId
    );

    @PostMapping("/user/{username}/all")
    ResponseEntity<ResponseObject> getAllCarts(
            @PathVariable String username,
            @RequestBody Filter filter
    );
    @PostMapping("/user/{username}/add")
    ResponseEntity<ResponseObject> addCart(
            @PathVariable String username,
            @RequestBody @Valid CartItemDto cartItemDto
    );
}
