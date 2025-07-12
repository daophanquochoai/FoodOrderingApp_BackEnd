package doctorhoai.learn.aiservice.controller;

import doctorhoai.learn.aiservice.service.semantic.SearchFoodService;
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

    private final SearchFoodService searchFoodService;

    @GetMapping
    public ResponseEntity<Object> searchByParam(
            @RequestParam String query
    ){
        return ResponseEntity.ok(
                searchFoodService.searchFood(query)
        );
    }

}
