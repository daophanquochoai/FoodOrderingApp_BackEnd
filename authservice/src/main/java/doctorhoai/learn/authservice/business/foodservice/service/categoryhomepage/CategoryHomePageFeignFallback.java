package doctorhoai.learn.authservice.business.foodservice.service.categoryhomepage;

import doctorhoai.learn.authservice.business.foodservice.model.CategoryHomepageDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryHomePageFeignFallback implements FallbackFactory<CategoryHomePageFeign> {

    private final HandleFallBack fallBack;


    @Override
    public CategoryHomePageFeign create(Throwable cause) {
        return new CategoryHomePageFeign() {
            @Override
            public ResponseEntity<ResponseObject> getAllCategoryHomepage() {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> addCategoryHomepage(CategoryHomepageDto categoryHomepageDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteCategoryHomepage(Integer id, CategoryHomepageDto categoryHomepageDto) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
