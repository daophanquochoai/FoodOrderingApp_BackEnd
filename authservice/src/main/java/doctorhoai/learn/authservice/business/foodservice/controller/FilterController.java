package doctorhoai.learn.authservice.business.foodservice.controller;

import doctorhoai.learn.authservice.business.foodservice.model.FilterOptions;
import doctorhoai.learn.authservice.business.foodservice.service.filterservice.FilterFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {

    private final FilterFeign filterFeign;

    @PostMapping("/all/{state}")
    public ResponseEntity<ResponseObject> getFilter(
            @PathVariable String state,
            @RequestBody FilterOptions filterOptions
    ){
        return filterFeign.getFilter(state, filterOptions);
    }
}
