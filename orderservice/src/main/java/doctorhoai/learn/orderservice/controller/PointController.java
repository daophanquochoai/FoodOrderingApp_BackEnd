package doctorhoai.learn.orderservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.dto.PointDto;
import doctorhoai.learn.orderservice.service.pointservice.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> getPointOfUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(pointService.getPoints(userId))
                        .message(EMessageResponse.GET_POINT_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createPoint(@RequestBody PointDto point) {
        pointService.createPoints(point);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_POINT_SUCCESSFUL.getMessage())
                        .build()
        );
    }
}
