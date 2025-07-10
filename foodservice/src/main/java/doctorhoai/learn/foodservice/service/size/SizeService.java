package doctorhoai.learn.foodservice.service.size;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.SizeDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;

public interface SizeService {

    SizeDto addSize(SizeDto dto);
    SizeDto updateSize(SizeDto dto, Integer id);
    PageObject getSizeByFilter(Filter filter);
}
