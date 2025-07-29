package doctorhoai.learn.orderservice.service.pointservice;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.PointDto;

public interface PointService {
    PointDto getPoints(Integer id);
    void createPoints(PointDto pointDto);
}
