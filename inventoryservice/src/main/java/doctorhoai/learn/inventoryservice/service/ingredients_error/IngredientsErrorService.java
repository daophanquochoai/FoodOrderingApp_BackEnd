package doctorhoai.learn.inventoryservice.service.ingredients_error;


import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.inventoryservice.dto.IngredientsErrorDto;
import doctorhoai.learn.inventoryservice.dto.filter.Filter;

public interface IngredientsErrorService {

    IngredientsErrorDto createIngredientsError(IngredientsErrorDto ingredientsErrorDto);
    IngredientsErrorDto updateIngredientsError(IngredientsErrorDto ingredientsErrorDto, Integer id);
    PageObject getIngredientsErrorPage(Filter filter);
}
