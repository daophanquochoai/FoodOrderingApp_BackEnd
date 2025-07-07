package doctorhoai.learn.inventoryservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.inventoryservice.dto.SourceDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;
import doctorhoai.learn.inventoryservice.service.source.SourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/source")
@RequiredArgsConstructor
public class SourceController {

    private final SourceService sourceService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addSource(
            @RequestBody @Valid SourceDto sourceDto
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(sourceService.createSource(sourceDto))
                        .message(EMessageResponse.CREATE_SOURCE.getMessage())
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllSource(
            @RequestBody Filter filter
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(sourceService.getAllSources(filter))
                        .message(EMessageResponse.GET_SOURCE.getMessage())
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateSource(
            @PathVariable Integer id,
            @RequestBody @Valid SourceDto sourceDto
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_SOURCE.getMessage())
                        .data(sourceService.updateSource(sourceDto, id))
                        .build()
        );
    }
}
