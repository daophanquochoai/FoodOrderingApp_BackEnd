package doctorhoai.learn.authservice.business.userservice.controller;

import doctorhoai.learn.authservice.business.userservice.model.AddressDto;
import doctorhoai.learn.authservice.business.userservice.model.Filter;
import doctorhoai.learn.authservice.business.userservice.service.address.AddressFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressFeign addressFeign;

    @PostMapping("/add")
    ResponseEntity<ResponseObject> createAddress(
            @RequestBody @Valid AddressDto addressDto
    ){
        return addressFeign.createAddress(addressDto);
    }

    @PostMapping("/all")
    ResponseEntity<ResponseObject> createAllAddress(
            @RequestBody Filter filter
    ){
        return addressFeign.createAllAddress(filter);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateAddress(
            @PathVariable Integer id,
            @RequestBody @Valid AddressDto addressDto
    ){
        return addressFeign.updateAddress(id, addressDto);
    }
}
