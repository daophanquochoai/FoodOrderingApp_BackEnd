package doctorhoai.learn.foodservice.service.size;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.foodservice.dto.SizeDto;
import doctorhoai.learn.foodservice.dto.filter.Filter;
import doctorhoai.learn.foodservice.exception.NameSizeDuplicateException;
import doctorhoai.learn.foodservice.exception.SizeNotFoundException;
import doctorhoai.learn.foodservice.model.Size;
import doctorhoai.learn.foodservice.repository.SizeRepository;
import doctorhoai.learn.foodservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService{

    private final SizeRepository sizeRepository;
    private final Mapper mapper;


    @Override
    public SizeDto addSize(SizeDto dto) {

        // check
        Optional<Size> sizeOptional = sizeRepository.getSizeByNameAndIsActive(dto.getName(), dto.getIsActive());
        if( sizeOptional.isPresent()) {
            throw new NameSizeDuplicateException();
        }

        Size size = mapper.convertToSize(dto);
        size.setIsActive(true);
        size = sizeRepository.save(size);
        return mapper.convertToSizeDto(size);
    }

    @Override
    public SizeDto updateSize(SizeDto dto, Integer id) {

        Optional<Size> sizeOptional = sizeRepository.getSizeByNameAndIsActive(dto.getName(), dto.getIsActive());
        if( sizeOptional.isPresent() && !sizeOptional.get().getId().equals(id)) {
            throw new NameSizeDuplicateException();
        }

        Size size = sizeRepository.findById(id).orElseThrow(SizeNotFoundException::new);
        size.setName(dto.getName());
        size.setIsActive(dto.getIsActive());
        size = sizeRepository.save(size);
        return mapper.convertToSizeDto(size);
    }

    @Override
    public PageObject getSizeByFilter(Filter filter) {
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }
        Page<Size> sizePage = sizeRepository.getSizeByFilter(
                filter.getSearch(),
                filter.getIsActive(),
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay(),
                pageable
        );

        List<Size> sizes = sizePage.getContent();
        return PageObject.builder()
                .page(pageable.getPageNumber())
                .totalPage(sizePage.getTotalPages())
                .data(sizes.stream().map(mapper::convertToSizeDto).toList())
                .build();
    }
}
