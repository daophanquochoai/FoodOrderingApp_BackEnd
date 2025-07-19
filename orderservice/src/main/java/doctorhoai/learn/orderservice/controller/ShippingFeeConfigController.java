package doctorhoai.learn.orderservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.dto.ShippingFeeConfigDto;
import doctorhoai.learn.orderservice.service.shipfeeconfig.ShippingFeeConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping_fee_config")
@RequiredArgsConstructor
public class ShippingFeeConfigController {

    private final ShippingFeeConfigService shippingFeeConfigService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createNewShippingFeeConfig(
            @RequestBody @Valid ShippingFeeConfigDto shippingFeeConfigDto
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_SHIPPING_FEE_SUCCESSFUL.getMessage())
                        .data(shippingFeeConfigService.createShippingFeeConfig(shippingFeeConfigDto))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getShippingFeeConfig(){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_SHIPPING_FEE_CONFIG_SUCCESSFUL.getMessage())
                        .data(shippingFeeConfigService.getShippingFeeConfig())
                        .build()
        );
    }
}
