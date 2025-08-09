package doctorhoai.learn.aiservice.controller;

import doctorhoai.learn.aiservice.service.semantic.SearchFoodService;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchFoodService searchFoodService;

    @GetMapping
    public ResponseEntity<ResponseObject> searchByParam(
            @RequestParam String query
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(searchFoodService.searchFoodDto(query))
                        .message(EMessageResponse.GET_SEARCH_SUCCESSFUL.getMessage())
                        .build()
        );
    }

}
