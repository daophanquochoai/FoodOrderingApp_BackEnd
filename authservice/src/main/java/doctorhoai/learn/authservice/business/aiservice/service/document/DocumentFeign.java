package doctorhoai.learn.authservice.business.aiservice.service.document;

import doctorhoai.learn.authservice.business.aiservice.model.Filter;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@FeignClient(
        name = "aiservice",
        path = "/api/document",
        contextId = "aiDocumentFeignBusiness",
        fallbackFactory = DocumentFeignFallback.class,
        configuration = FeignConfig.class
)
public interface DocumentFeign {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResponseObject> uploadDocument(@RequestPart("file") MultipartFile file);

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllIngredients(
            @RequestBody Filter filter
    );

    @GetMapping("/download/{id}")
    ResponseEntity<byte[]> downloadDocument(@PathVariable("id") Integer id) throws IOException;
}
