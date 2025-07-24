package doctorhoai.learn.foodservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.dto.filter.FilterOptions;
import doctorhoai.learn.foodservice.service.filter.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;

    @PostMapping("/all/{state}")
    public ResponseEntity<ResponseObject> getFilter(
            @PathVariable String state,
            @RequestBody FilterOptions filterOptions
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_FILTER.getMessage())
                        .data(filterService.getFilter(filterOptions, state))
                        .build()
        );
    }
}
