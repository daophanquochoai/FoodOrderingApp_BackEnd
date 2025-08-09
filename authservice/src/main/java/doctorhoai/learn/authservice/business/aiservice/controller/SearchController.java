package doctorhoai.learn.authservice.business.aiservice.controller;

import doctorhoai.learn.authservice.business.aiservice.service.search.SearchFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchFeign searchFeign;

    @GetMapping
    ResponseEntity<ResponseObject> searchByParam(
            @RequestParam String query
    ){
        return searchFeign.searchByParam(query);
    }
}
