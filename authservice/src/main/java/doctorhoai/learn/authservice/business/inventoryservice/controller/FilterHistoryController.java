package doctorhoai.learn.authservice.business.inventoryservice.controller;

import doctorhoai.learn.authservice.business.inventoryservice.model.FilterOption;
import doctorhoai.learn.authservice.business.inventoryservice.service.filter.FilterFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history/filter")
@RequiredArgsConstructor
public class FilterHistoryController {

    private final FilterFeign filterFeign;

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getFilterByAll(
            @RequestBody FilterOption filterOption
    ){
        return filterFeign.getFilterByAll(filterOption);
    }
}
