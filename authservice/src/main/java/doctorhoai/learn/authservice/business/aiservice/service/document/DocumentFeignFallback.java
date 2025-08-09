package doctorhoai.learn.authservice.business.aiservice.service.document;

import doctorhoai.learn.authservice.business.aiservice.model.Filter;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DocumentFeignFallback implements FallbackFactory<DocumentFeign> {

    private final HandleFallBack fallBack;

    @Override
    public DocumentFeign create(Throwable cause) {
        return new DocumentFeign(){
            @Override
            public ResponseEntity<ResponseObject> uploadDocument(MultipartFile file) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllIngredients(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<byte[]> downloadDocument(Integer id) throws IOException {
                return null;
            }
        };
    }
}
