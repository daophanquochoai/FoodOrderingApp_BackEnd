package doctorhoai.learn.foodservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.VoucherDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.model.Voucher;
import doctorhoai.learn.foodservice.service.voucher.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("/user")
    public ResponseEntity<ResponseObject> getVoucherOfUser(
            @RequestBody Filter filter
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_VOUCHER.getMessage())
                        .data(voucherService.getVoucherOfUser(filter))
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addVoucher(@RequestBody @Valid VoucherDto voucherDto){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(voucherService.createVoucher(voucherDto))
                        .message(EMessageResponse.CREATE_VOUCHER.getMessage())
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllVoucher(
            @RequestBody Filter filter
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_VOUCHER.getMessage())
                        .data(voucherService.getAllVouchers(filter))
                        .build()
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseObject> getVoucherByCode(
            @PathVariable String code
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_VOUCHER.getMessage())
                        .data(voucherService.getVoucherByCode(code))
                        .build()
        );
    }
}
