package doctorhoai.learn.inventoryservice.service.source;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.inventoryservice.dto.SourceDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;


public interface SourceService {
    PageObject getAllSources(Filter filter);
    SourceDto createSource(SourceDto source);
    SourceDto updateSource(SourceDto source, Integer id);
}
