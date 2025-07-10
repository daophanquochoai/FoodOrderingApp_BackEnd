package doctorhoai.learn.userservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.userservice.controller.contanst.EMessageResponse;
import doctorhoai.learn.userservice.dto.AddressDto;
import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.service.address.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createAddress(
            @RequestBody @Valid AddressDto addressDto
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.ADD_ADDRESS.getMessage())
                        .data(addressService.createNewAddress(addressDto))
                        .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> createAllAddress(
            @RequestBody Filter filter
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_ADDRESS_SUCCESS.getMessage())
                        .data(addressService.getAddress(filter))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateAddress(
            @PathVariable Integer id,
            @RequestBody @Valid AddressDto addressDto
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_ADDRESS.getMessage())
                        .data(addressService.updateAddress(addressDto, id
                        ))
                        .build()
        );
    }
}
