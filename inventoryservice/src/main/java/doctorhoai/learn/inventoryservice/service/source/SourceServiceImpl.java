package doctorhoai.learn.inventoryservice.service.source;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.inventoryservice.dto.SourceDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;
import doctorhoai.learn.inventoryservice.exception.exception.SourceNotFoundException;
import doctorhoai.learn.inventoryservice.mapper.Mapper;
import doctorhoai.learn.inventoryservice.model.Source;
import doctorhoai.learn.inventoryservice.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;
    private final Mapper mapper;

    @Override
    public PageObject getAllSources(Filter filter) {
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        } else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }
        Page<Source> sources = sourceRepository.getAllSources(
                filter.getSearch(),
                filter.getIsActive(),
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay(),
                pageable
        );

        PageObject pageObject = PageObject.builder()
                .page(filter.getPageNo())
                .totalPage(sources.getTotalPages())
                .data( sources.stream().map(mapper::convertToSourceDto).toList())
                .build();
        return pageObject;
    }

    @Override
    public SourceDto createSource(SourceDto sourceDto) {
        Source source = mapper.convertToSource(sourceDto);
        source = sourceRepository.save(source);
        return mapper.convertToSourceDto(source);
    }

    @Override
    public SourceDto updateSource(SourceDto sourceDto, Integer id) {
        Source source = sourceRepository.findById(id).orElseThrow(SourceNotFoundException::new);

        source.setName(sourceDto.getName());
        source.setAddress(sourceDto.getAddress());
        source.setPhoneNumber(sourceDto.getPhoneNumber());
        source.setEmail(sourceDto.getEmail());
        source.setLink(sourceDto.getLink());
        source.setTaxCode(sourceDto.getTaxCode());
        source.setIsActive(sourceDto.getIsActive());

        source = sourceRepository.save(source);
        return mapper.convertToSourceDto(source);
    }
}
