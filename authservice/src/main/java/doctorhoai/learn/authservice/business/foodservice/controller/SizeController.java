package doctorhoai.learn.authservice.business.foodservice.controller;

import doctorhoai.learn.authservice.business.foodservice.model.Filter;
import doctorhoai.learn.authservice.business.foodservice.model.SizeDto;
import doctorhoai.learn.authservice.business.foodservice.service.sizeservice.SizeFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/size")
public class SizeController {

    private final SizeFeign sizeFeign;

    @PostMapping("/add")
    ResponseEntity<ResponseObject> createNewSize(
            @RequestBody @Valid SizeDto sizeDto
    ){
        return sizeFeign.createNewSize(sizeDto);
    }
    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateSize(
            @PathVariable Integer id,
            @RequestBody @Valid SizeDto sizeDto
    ){
        return sizeFeign.updateSize(id, sizeDto);
    }

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllSize(
            @RequestBody Filter filter
    ){
        return sizeFeign.getAllSize(filter);
    }
}
