package doctorhoai.learn.authservice.business.inventoryservice.controller;

import doctorhoai.learn.authservice.business.inventoryservice.model.Filter;
import doctorhoai.learn.authservice.business.inventoryservice.model.SourceDto;
import doctorhoai.learn.authservice.business.inventoryservice.service.source.SourceFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/source")
@RequiredArgsConstructor
public class SourceController {

    private final SourceFeign sourceFeign;
    @PostMapping("/add")
    ResponseEntity<ResponseObject> addSource(
            @RequestBody @Valid SourceDto sourceDto
    ){
        return sourceFeign.addSource(sourceDto);
    }

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllSource(
            @RequestBody Filter filter
    ){
        return sourceFeign.getAllSource(filter);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateSource(
            @PathVariable Integer id,
            @RequestBody @Valid SourceDto sourceDto
    ){
        return sourceFeign.updateSource(id, sourceDto);
    }
}
