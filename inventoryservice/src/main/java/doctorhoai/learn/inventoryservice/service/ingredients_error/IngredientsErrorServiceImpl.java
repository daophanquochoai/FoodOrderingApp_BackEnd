package doctorhoai.learn.inventoryservice.service.ingredients_error;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.inventoryservice.dto.IngredientsErrorDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;
import doctorhoai.learn.inventoryservice.exception.exception.IngredientsErrorNotFoundException;
import doctorhoai.learn.inventoryservice.mapper.Mapper;
import doctorhoai.learn.inventoryservice.model.HistoryIngredients;
import doctorhoai.learn.inventoryservice.model.IngredientError;
import doctorhoai.learn.inventoryservice.repository.HistoryIngredientsRepository;
import doctorhoai.learn.inventoryservice.repository.IngredientsErrorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class IngredientsErrorServiceImpl implements IngredientsErrorService {

    private final IngredientsErrorRepository ingredientsErrorRepository;
    private final HistoryIngredientsRepository historyIngredientsRepository;
    private final Mapper mapper;

    @Override
    public IngredientsErrorDto createIngredientsError(IngredientsErrorDto ingredientsErrorDto) {
        IngredientError ingredientError = mapper.convertToIngredientError(ingredientsErrorDto);

        // check batch_code
        if( ingredientsErrorDto.getHistoryIngredients() == null || ingredientsErrorDto.getHistoryIngredients().getId() == null){
            throw new IngredientsErrorNotFoundException();
        }

        HistoryIngredients historyIngredients = historyIngredientsRepository.findById(ingredientsErrorDto.getHistoryIngredients().getId()).orElseThrow(IngredientsErrorNotFoundException::new);

        ingredientError.setHistoryIngredients(historyIngredients);
        ingredientError = ingredientsErrorRepository.save(ingredientError);
        return mapper.convertToIngredientsErrorDto(ingredientError);
    }

    @Override
    public IngredientsErrorDto updateIngredientsError(IngredientsErrorDto ingredientsErrorDto, Integer id) {
        IngredientError ingredientError = ingredientsErrorRepository.findById(id).orElseThrow(IngredientsErrorNotFoundException::new);
        ingredientError.setUnit(ingredientsErrorDto.getUnit());
        ingredientError.setQuantity(ingredientsErrorDto.getQuantity());
        ingredientError.setReason(ingredientsErrorDto.getReason());
        ingredientError.setIsActive(ingredientsErrorDto.getIsActive());

        ingredientError = ingredientsErrorRepository.save(ingredientError);
        return mapper.convertToIngredientsErrorDto(ingredientError);
    }

    @Override
    public PageObject getIngredientsErrorPage(Filter filter) {
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize());
        }
        Page<IngredientError> ingredientErrors = ingredientsErrorRepository.getIngredientsErrorByFilter(
                filter.getIsActive(),
                filter.getSearch(),
                filter.getMinQuantity(),
                filter.getMaxQuantity(),
                filter.getUnits(),
                pageable
        );

        List<IngredientError> iError = ingredientErrors.getContent();

        List<IngredientsErrorDto> iErrorDto = iError.stream().map(mapper::convertToIngredientsErrorDto).toList();

        return PageObject.builder()
                .totalPage(ingredientErrors.getTotalPages())
                .page(filter.getPageNo())
                .data(iErrorDto)
                .build();
    }
}
