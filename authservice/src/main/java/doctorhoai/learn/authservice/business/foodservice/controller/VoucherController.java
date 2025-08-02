package doctorhoai.learn.authservice.business.foodservice.controller;


import doctorhoai.learn.authservice.business.foodservice.model.ExportVoucher;
import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.VoucherDto;
import doctorhoai.learn.authservice.business.foodservice.service.voucherservice.VoucherFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherFeign voucherFeign;

    @GetMapping("/user")
    ResponseEntity<ResponseObject> getVoucherOfUser(
            @RequestBody Filter filter
    ){
        return voucherFeign.getVoucherOfUser(filter);
    }

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addVoucher(@RequestBody @Valid VoucherDto voucherDto){
        return voucherFeign.addVoucher(voucherDto);
    }

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllVoucher(
            @RequestBody Filter filter
    ){
        return voucherFeign.getAllVoucher(filter);
    }

    @GetMapping("/{code}")
    ResponseEntity<ResponseObject> getVoucherByCode(
            @PathVariable String code
    ){
        return voucherFeign.getVoucherByCode(code);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateVoucherById(
            @PathVariable Integer id,
            @RequestBody VoucherDto voucherDto
    ){
        return voucherFeign.updateVoucherById(id, voucherDto);
    }
    @PostMapping("/export/voucher")
    public ResponseEntity<ResponseObject> exportVoucehr(
            @RequestBody ExportVoucher exportVoucher
    ){
        return voucherFeign.exportVoucehr(exportVoucher);
    }
}
