package doctorhoai.learn.inventoryservice.service.ingredients;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.inventoryservice.dto.IngredientsDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;


public interface IngredientsService {

    IngredientsDto createIngredients(IngredientsDto ingredientsDto);
    PageObject getAllIngredients(Filter filter);
    IngredientsDto getIngredients(Integer id);
    IngredientsDto updateIngredients(IngredientsDto ingredientsDto, Integer id);
}
