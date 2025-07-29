package doctorhoai.learn.orderservice.service.pointservice;

import doctorhoai.learn.orderservice.dto.PointDto;
import doctorhoai.learn.orderservice.exception.exception.PointNotFound;
import doctorhoai.learn.orderservice.model.Point;
import doctorhoai.learn.orderservice.repository.PointRepository;
import doctorhoai.learn.orderservice.utils.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final Mapper mapper;

    @Override
    public PointDto getPoints(Integer id) {
        return mapper.convertToPointDto(pointRepository.findByUserIdAndIsActive(id, true).orElseThrow(PointNotFound::new));
    }

    @Override
    public void createPoints(PointDto pointDto) {
        Point point = mapper.convertToPoint(pointDto);
        point.setUserId(pointDto.getUserId().getId());
        pointRepository.save(point);
    }
}
