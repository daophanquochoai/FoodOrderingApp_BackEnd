package doctorhoai.learn.inventoryservice.service.ingredients;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.inventoryservice.dto.IngredientsDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;
import doctorhoai.learn.inventoryservice.exception.exception.IngredientsNotFoundException;
import doctorhoai.learn.inventoryservice.mapper.Mapper;
import doctorhoai.learn.inventoryservice.model.Ingredients;
import doctorhoai.learn.inventoryservice.repository.IngredientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientsServiceImpl implements IngredientsService {

    private final IngredientsRepository ingredientsRepository;
    private final Mapper mapper;

    @Override
    public IngredientsDto createIngredients(IngredientsDto ingredientsDto) {

        Ingredients ingredients = mapper.convertToIngredients(ingredientsDto);
        ingredients = ingredientsRepository.save(ingredients);
        return mapper.convertToIngredientsDto(ingredients);
    }

    @Override
    public PageObject getAllIngredients(Filter filter) {
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }
        Page<Ingredients> page = ingredientsRepository.findIngredientsByFilter(
                filter.getSearch(),
                filter.getUnit() == null || filter.getUnit().size() == 0 ? null : filter.getUnit(),
                filter.getMinThreshold(),
                filter.getMaxThreshold(),
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay(),
                filter.getIsActive(),
                pageable
        );
        List<Ingredients> ingredientsDtos = page.getContent();

        return PageObject.builder()
                .totalPage((int) page.getTotalElements())
                .page(filter.getPageNo())
                .data(ingredientsDtos.stream().map(mapper::convertToIngredientsDto))
                .build();
    }

    @Override
    public IngredientsDto getIngredients(Integer id) {
        Ingredients ingredients = ingredientsRepository.findById(id).orElseThrow(IngredientsNotFoundException::new);
        return mapper.convertToIngredientsDto(ingredients);
    }

    @Override
    public IngredientsDto updateIngredients(IngredientsDto ingredientsDto, Integer id) {
        Ingredients ingredients = ingredientsRepository.findById(id).orElseThrow(IngredientsNotFoundException::new);

        ingredients.setName(ingredientsDto.getName());
        ingredients.setUnit(ingredientsDto.getUnit());
        ingredients.setIsActive(ingredientsDto.getIsActive());
        ingredients.setLowThreshold(ingredientsDto.getLowThreshold());

        ingredients = ingredientsRepository.save(ingredients);

        return mapper.convertToIngredientsDto(ingredients);
    }
}
