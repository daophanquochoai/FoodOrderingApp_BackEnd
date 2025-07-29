package doctorhoai.learn.authservice.business.orderservice.controller;

import doctorhoai.learn.authservice.business.orderservice.service.shipservice.ShipFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shipping_fee_config")
@RequiredArgsConstructor
public class ShippingFeeConfigController {

    private final ShipFeign shipFeign;

    @GetMapping
    public ResponseEntity<ResponseObject> getShippingFeeConfig(){
        return shipFeign.getShippingFeeConfig();
    }
}
