package doctorhoai.learn.foodservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.SizeDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.service.size.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/size")
@RequiredArgsConstructor
public class SizeController {

    private final SizeService sizeService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createNewSize(
            @RequestBody @Valid SizeDto sizeDto
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_SIZE_SUCCESSFUL.getMessage())
                        .data(sizeService.addSize(sizeDto))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateSize(
            @PathVariable Integer id,
            @RequestBody @Valid SizeDto sizeDto
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_SIZE_SUCCESSFUL.getMessage())
                        .data(sizeService.updateSize(sizeDto, id))
                        .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getAllSize(
            @RequestBody Filter filter
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(sizeService.getSizeByFilter(filter))
                        .message(EMessageResponse.GET_SIZE_SUCCESSFUL.getMessage())
                        .build()
        );
    }
}
