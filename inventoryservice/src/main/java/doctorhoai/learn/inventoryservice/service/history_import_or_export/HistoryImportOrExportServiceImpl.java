package doctorhoai.learn.inventoryservice.service.history_import_or_export;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.inventoryservice.dto.*;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;
import doctorhoai.learn.inventoryservice.exception.exception.HistoryImportAndExportNotFound;
import doctorhoai.learn.inventoryservice.exception.exception.HistoryImportDuplicate;
import doctorhoai.learn.inventoryservice.exception.exception.IngredientsNotFoundException;
import doctorhoai.learn.inventoryservice.exception.exception.SourceNotFoundException;
import doctorhoai.learn.inventoryservice.mapper.Mapper;
import doctorhoai.learn.inventoryservice.model.*;
import doctorhoai.learn.inventoryservice.repository.HistoryImportOrExportRepository;
import doctorhoai.learn.inventoryservice.repository.IngredientsRepository;
import doctorhoai.learn.inventoryservice.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryImportOrExportServiceImpl implements HistoryImportOrExportService{

    private final HistoryImportOrExportRepository historyImportOrExportRepository;
    private final IngredientsRepository ingredientsRepository;
    private final SourceRepository sourceRepository;
    private final Mapper mapper;

    @Override
    public PageObject getHistoryImportOrExportByFilter(Filter filter) {
        Pageable pageable;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }

        Page<HistoryImportOrExport> historyImportOrExportList = historyImportOrExportRepository.getHistoryImportOrExportByFilter(
                filter.getHistoryImportOrExportId() == null || filter.getHistoryImportOrExportId().isEmpty() ? null : filter.getHistoryImportOrExportId(),
                filter.getSourceId() == null || filter.getSourceId().isEmpty() ? null : filter.getSourceId(),
                filter.getInventory(),
                filter.getIsActive(),
                filter.getIngredientsId() == null || filter.getIngredientsId().isEmpty() ? null : filter.getIngredientsId(),
                filter.getMinPrice(),
                filter.getMaxPrice(),
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay(),
                filter.getSearch(),
                pageable
        );
        List<HistoryImportOrExport> data = historyImportOrExportList.getContent();

        PageObject pageObject = PageObject.builder()
                .page(filter.getPageNo())
                .totalPage(historyImportOrExportList.getTotalPages())
                .build();
        if( filter.getDeep() == 0 ){
            //convert
            List<HistoryImportOrExportDto> historyImportOrExportDtos = data.stream().map(mapper::convertToHistoryImportOrExportDto).collect(Collectors.toList());
            pageObject.setData(historyImportOrExportDtos);
        }else{
            pageObject.setData(convertToHistoryImportOrExportDto(data));
        }
        return pageObject;
    }

    private List<HistoryImportOrExportDto> convertToHistoryImportOrExportDto( List<HistoryImportOrExport> historyImportOrExportList ){
        List<HistoryImportOrExportDto> returnValue = new ArrayList<>();
        for( HistoryImportOrExport historyImportOrExport : historyImportOrExportList ){
            HistoryImportOrExportDto historyImportOrExportDto = mapper.convertToHistoryImportOrExportDto(historyImportOrExport);
            if( historyImportOrExport.getSourceId() != null ){
                historyImportOrExportDto.setSource(mapper.convertToSourceDto(historyImportOrExport.getSourceId()));
            }
            if( historyImportOrExport != null && historyImportOrExport.getHistoryIngredients() != null && !historyImportOrExport.getHistoryIngredients().isEmpty()){
                historyImportOrExportDto.setHistoryIngredients(convertToHistoryIngredientsDto(historyImportOrExport.getHistoryIngredients()));
            }
            returnValue.add(historyImportOrExportDto);
        }
        return returnValue;
    }

    private List<HistoryIngredientsDto> convertToHistoryIngredientsDto( List<HistoryIngredients> historyIngredientsList){
        List<HistoryIngredientsDto> returnValue = new ArrayList<>();
        for( HistoryIngredients historyIngredients : historyIngredientsList ){
            HistoryIngredientsDto historyIngredientsDto = mapper.convertToHistoryIngredientsDto(historyIngredients);

            if( historyIngredients != null){
                if( historyIngredients.getErrors() != null && !historyIngredients.getErrors().isEmpty() ){
                    historyIngredientsDto.setErrors(convertToHistoryIngredientsErrorDto(historyIngredients.getErrors()));
                }
                if( historyIngredients.getUses() != null && !historyIngredients.getUses().isEmpty() ){
                    historyIngredientsDto.setUses(convertToHistoryIngredientsUseDto(historyIngredients.getUses()));
                }
                if( historyIngredients.getIngredientsId() != null ){
                    historyIngredientsDto.setIngredients(mapper.convertToIngredientsDto(historyIngredients.getIngredientsId()));
                }
            }
            returnValue.add(historyIngredientsDto);
        }
        return returnValue;
    }

    private List<IngredientsErrorDto> convertToHistoryIngredientsErrorDto( List<IngredientError> ingredientErrors){
        List<IngredientsErrorDto> returnValue = new ArrayList<>();
        for( IngredientError ingredientError : ingredientErrors ){
            returnValue.add(mapper.convertToIngredientsErrorDto(ingredientError));
        }
        return returnValue;
    }

    private List<IngredientsUseDto> convertToHistoryIngredientsUseDto( List<IngredientsUse> ingredientsUseList){
        List<IngredientsUseDto> returnValue = new ArrayList<>();
        for( IngredientsUse ingredientsUse : ingredientsUseList ){
            returnValue.add(mapper.convertToIngredientsUseDto(ingredientsUse));
        }
        return returnValue;
    }

    @Override
    public HistoryImportOrExportDto createHistoryImportOrExport(HistoryImportOrExportDto historyImportOrExportDto) {
        Optional<HistoryImportOrExport> historyImportOrExportOptional = historyImportOrExportRepository.getByBathCode(historyImportOrExportDto.getBathCode());
        if( historyImportOrExportOptional.isPresent()){
            throw new HistoryImportDuplicate();
        }
        HistoryImportOrExport historyImportOrExport = mapper.convertToHistoryImportOrExport(historyImportOrExportDto);
        historyImportOrExport.setIsActive(true);

        if(historyImportOrExportDto.getSource() == null || historyImportOrExportDto.getSource().getId() == null){
            throw new SourceNotFoundException();
        }else{
            Source source = sourceRepository.findById(historyImportOrExportDto.getSource().getId()).orElseThrow(SourceNotFoundException::new);
            historyImportOrExport.setSourceId(source);
        }

        if( historyImportOrExportDto.getHistoryIngredients() != null ){
            List<HistoryIngredients> historyIngredients = convertToHistoryIngredientsList(historyImportOrExportDto.getHistoryIngredients());
            for( HistoryIngredients historyIngredient : historyIngredients ){
                historyIngredient.setHistory(historyImportOrExport);
            }
            historyImportOrExport.setHistoryIngredients(historyIngredients);
        }
        historyImportOrExport = historyImportOrExportRepository.save(historyImportOrExport);
        return convertToHistoryImportOrExportDto(historyImportOrExport);
    }

    @Override
    public void updateHistoryImportOrExport(Integer id) {
        HistoryImportOrExport historyImportOrExport = historyImportOrExportRepository.findById(id).orElseThrow(HistoryImportAndExportNotFound::new);
        historyImportOrExport.setIsActive(false);
        historyImportOrExportRepository.save(historyImportOrExport);
    }

    private List<Ingredients> getIngredientsByIds( List<Integer> ids ){
        List<Ingredients> ingredients = ingredientsRepository.findIngredientsByIds(ids);
        if( ingredients.size() != ids.size() ){
            throw new IngredientsNotFoundException();
        }
        return ingredients;
    }

    private List<HistoryIngredients> convertToHistoryIngredientsList(List<HistoryIngredientsDto> historyIngredientsDtos){

        List<Integer> idsIngredients = historyIngredientsDtos.stream().map(i -> i.getIngredients().getId()).filter(Objects::nonNull).distinct().toList();
        List<Ingredients> ingredientsList = getIngredientsByIds(idsIngredients);
        List<HistoryIngredients> historyIngredientsList = new ArrayList<>();
        for( HistoryIngredientsDto historyIngredientsDto : historyIngredientsDtos ){
            HistoryIngredients historyIngredients = mapper.convertToHistoryIngredients(historyIngredientsDto);
            if( historyIngredientsDto.getIngredients() != null){
                Ingredients ingredients = ingredientsList.stream().filter(i -> i.getId() == historyIngredientsDto.getIngredients().getId()).findFirst().orElseThrow(IngredientsNotFoundException::new);
                historyIngredients.setIngredientsId(ingredients);
            }
            historyIngredientsList.add(historyIngredients);
        }
        return historyIngredientsList;
    }

    private List<HistoryImportOrExportDto> convertToHistoryImportOrExportDtoWithDeep(List<HistoryImportOrExport> historyImportOrExportList){
        List<HistoryImportOrExportDto> historyImportOrExportDtos = new ArrayList<>();
        for( HistoryImportOrExport historyImportOrExport : historyImportOrExportList ){
            HistoryImportOrExportDto historyImportOrExportDto = convertToHistoryImportOrExportDto(historyImportOrExport);
            historyImportOrExportDtos.add(historyImportOrExportDto);
        }
        return historyImportOrExportDtos;
    }

    private HistoryImportOrExportDto convertToHistoryImportOrExportDto(HistoryImportOrExport historyImportOrExport){
        HistoryImportOrExportDto historyImportOrExportDto = mapper.convertToHistoryImportOrExportDto(historyImportOrExport);
        if( historyImportOrExport.getHistoryIngredients() != null && !historyImportOrExport.getHistoryIngredients().isEmpty()){
            List<HistoryIngredientsDto> historyIngredientsDtos = convertToHistoryIngredientsWithDeep(historyImportOrExport.getHistoryIngredients());
            historyImportOrExportDto.setHistoryIngredients(historyIngredientsDtos);
        }
        if( historyImportOrExport.getSourceId() != null){
            Source source = sourceRepository.findById(historyImportOrExport.getSourceId().getId()).orElseThrow(SourceNotFoundException::new);
            historyImportOrExportDto.setSource(mapper.convertToSourceDto(source));
        }
        return historyImportOrExportDto;
    }

    private List<HistoryIngredientsDto> convertToHistoryIngredientsWithDeep( List<HistoryIngredients> historyIngredients){
        List<HistoryIngredientsDto> historyIngredientsDtos = new ArrayList<>();
        for( HistoryIngredients his : historyIngredients ){
            HistoryIngredientsDto historyIngredientsDto = mapper.convertToHistoryIngredientsDto(his);
            if( his.getIngredientsId() != null ){
                IngredientsDto ingredientsDto = mapper.convertToIngredientsDto(his.getIngredientsId());
                historyIngredientsDto.setIngredients(ingredientsDto);
            }
            historyIngredientsDtos.add(historyIngredientsDto);
        }
        return historyIngredientsDtos;
    }

}
