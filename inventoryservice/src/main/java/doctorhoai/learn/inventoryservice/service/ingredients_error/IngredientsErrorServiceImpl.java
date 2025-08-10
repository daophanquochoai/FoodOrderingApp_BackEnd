package doctorhoai.learn.inventoryservice.service.ingredients_error;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.inventoryservice.dto.*;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;
import doctorhoai.learn.inventoryservice.exception.exception.IngredientsErrorNotFoundException;
import doctorhoai.learn.inventoryservice.mapper.Mapper;
import doctorhoai.learn.inventoryservice.model.HistoryImportOrExport;
import doctorhoai.learn.inventoryservice.model.HistoryIngredients;
import doctorhoai.learn.inventoryservice.model.IngredientError;
import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import doctorhoai.learn.inventoryservice.repository.HistoryImportOrExportRepository;
import doctorhoai.learn.inventoryservice.repository.HistoryIngredientsRepository;
import doctorhoai.learn.inventoryservice.repository.IngredientsErrorRepository;
import doctorhoai.learn.inventoryservice.service.history_import_or_export.HistoryImportOrExportService;
import doctorhoai.learn.inventoryservice.service.history_import_or_export.HistoryImportOrExportServiceImpl;
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
public class IngredientsErrorServiceImpl implements IngredientsErrorService {

    private final IngredientsErrorRepository ingredientsErrorRepository;
    private final HistoryIngredientsRepository historyIngredientsRepository;
    private final Mapper mapper;
    private final HistoryImportOrExportServiceImpl historyImportOrExportService;

    @Override
    public IngredientsErrorDto createIngredientsError(IngredientsErrorDto ingredientsErrorDto) {
        IngredientError ingredientError = mapper.convertToIngredientError(ingredientsErrorDto);

        // check batch_code
        if( ingredientsErrorDto.getHistoryIngredients() == null || ingredientsErrorDto.getHistoryIngredients().getId() == null){
            throw new IngredientsErrorNotFoundException();
        }

        HistoryIngredients historyIngredients = historyIngredientsRepository.findById(ingredientsErrorDto.getHistoryIngredients().getId()).orElseThrow(IngredientsErrorNotFoundException::new);

//        if (ingredientError.getQuantity() < historyIngredients.getQuantity()) {
//            historyIngredients.setQuantity(historyIngredients.getQuantity() - ingredientError.getQuantity());
//        } else {
//            ingredientError.setQuantity(Math.round(historyIngredients.getQuantity()));
//            historyIngredients.setQuantity(0.0F);
//        }
//        historyIngredients = historyIngredientsRepository.save(historyIngredients);

        ingredientError.setHistoryIngredients(historyIngredients);
        ingredientError = ingredientsErrorRepository.save(ingredientError);
        return mapper.convertToIngredientsErrorDto(ingredientError);
    }

    @Override
    public IngredientsErrorDto updateIngredientsError(IngredientsErrorDto ingredientsErrorDto, Integer id) {
        IngredientError ingredientError = ingredientsErrorRepository.findById(id).orElseThrow(IngredientsErrorNotFoundException::new);

        historyIngredientsRepository.findById(ingredientsErrorDto.getHistoryIngredients().getId()).orElseThrow(IngredientsErrorNotFoundException::new);

//        historyIngredients.setQuantity(historyIngredients.getQuantity() + ingredientError.getQuantity());
//        if (ingredientsErrorDto.getIsActive() == true) {
//            if (historyIngredients.getQuantity() > ingredientsErrorDto.getQuantity()) {
//                historyIngredients.setQuantity(historyIngredients.getQuantity() - ingredientsErrorDto.getQuantity());
//            } else {
//                ingredientError.setQuantity(Math.round(historyIngredients.getQuantity()));
//                historyIngredients.setQuantity(0.0F);
//            }
//        }
//        historyIngredientsRepository.save(historyIngredients);

        ingredientError.setIsActive(ingredientsErrorDto.getIsActive());
        ingredientError.setUnit(ingredientsErrorDto.getUnit());
        ingredientError.setQuantity(ingredientsErrorDto.getQuantity());
        ingredientError.setReason(ingredientsErrorDto.getReason());

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

        List<IngredientsErrorResponseDto> iErrorDto = iError.stream().map(ie ->{
            HistoryImportOrExportDto batchCode = null;
            String name = null;
            Integer historyId = null;
            EUnitType unit = ie.getUnit();
            Integer quantity = ie.getQuantity();
            String reason = ie.getReason();
            Boolean isActive = ie.getIsActive();
            Integer id = ie.getId();

            if (ie.getHistoryIngredients() != null) {
                HistoryIngredients hi = ie.getHistoryIngredients();
                historyId = hi.getId();

                if (hi.getHistory() != null) {
                    batchCode = mapper.convertToHistoryImportOrExportDto(hi.getHistory());
                }

                if (ie.getHistoryIngredients().getIngredientsId() != null) {
                    name = ie.getHistoryIngredients().getIngredientsId().getName();
                }
            }
            return new IngredientsErrorResponseDto(id, unit, quantity, reason, isActive, batchCode, name, historyId);
        }).toList();

        return PageObject.builder()
                .totalPage(ingredientErrors.getTotalPages())
                .page(filter.getPageNo())
                .data(iErrorDto)
                .build();
    }

    @Override
    public List<HistoryIngredientsDto> getHistoryIngredientsByHistoryId(Integer historyId) {
        List<HistoryIngredients> historyIngredients = historyIngredientsRepository.findByHistoryId(historyId);
        return historyImportOrExportService.convertToHistoryIngredientsDto(historyIngredients);
    }
}
