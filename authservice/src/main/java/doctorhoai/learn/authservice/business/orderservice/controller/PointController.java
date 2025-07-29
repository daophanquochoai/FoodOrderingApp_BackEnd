package doctorhoai.learn.authservice.business.orderservice.controller;

import doctorhoai.learn.authservice.business.orderservice.service.pointservice.PointFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("point")
@RequiredArgsConstructor
public class PointController {

    private final PointFeign pointFeign;

    @GetMapping("/{userId}")
    ResponseEntity<ResponseObject> getPointOfUser(@PathVariable("userId") int userId){
        return pointFeign.getPointOfUser(userId);
    }
}
