package doctorhoai.learn.inventoryservice.service.source;

import doctorhoai.learn.inventoryservice.dto.SourceDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;

import java.util.List;

public interface SourceService {
    List<SourceDto> getAllSources(Filter filter);
    SourceDto createSource(SourceDto source);
    SourceDto updateSource(SourceDto source, Integer id);
}
