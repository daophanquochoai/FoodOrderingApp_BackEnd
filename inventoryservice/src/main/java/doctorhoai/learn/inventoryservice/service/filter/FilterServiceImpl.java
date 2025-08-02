package doctorhoai.learn.inventoryservice.service.filter;

import doctorhoai.learn.inventoryservice.dto.filter.EFilter;
import doctorhoai.learn.inventoryservice.dto.filter.FilterOption;
import doctorhoai.learn.inventoryservice.mapper.Mapper;
import doctorhoai.learn.inventoryservice.model.Ingredients;
import doctorhoai.learn.inventoryservice.model.Source;
import doctorhoai.learn.inventoryservice.repository.IngredientsRepository;
import doctorhoai.learn.inventoryservice.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService{

    private final SourceRepository sourceRepository;
    private final IngredientsRepository ingredientsRepository;
    private final Mapper mapper;

    @Override
    public Map<String, Object> getFilter(FilterOption option) {
        Map<String, Object> result = new HashMap<>();
        if( option.getFilters().contains(EFilter.SOURCE)){
            List<Source> sources = sourceRepository.getSourceByIsActive(true);
            result.put("source",
                    sources.stream().map(mapper::convertToSourceDto)
                    );
        }
        if (option.getFilters().contains(EFilter.INGREDIENTS)) {
            List<Ingredients> ingredients = ingredientsRepository.getIngredientsByIsActive(true);
            result.put("ingredient", ingredients.stream().map(mapper::convertToIngredientsDto));
        }
        return result;
    }
}
