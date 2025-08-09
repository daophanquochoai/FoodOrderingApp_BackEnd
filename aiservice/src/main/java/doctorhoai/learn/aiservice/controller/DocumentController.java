package doctorhoai.learn.aiservice.controller;

import doctorhoai.learn.aiservice.dto.DocumentDto;
import doctorhoai.learn.aiservice.dto.filter.Filter;
import doctorhoai.learn.aiservice.service.document.DocumentService;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping(value = "/upload",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResponseObject> uploadDocument(
            @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(
            ResponseObject.builder()
                    .message(EMessageResponse.UPLOAD_SUCCESSFUL.getMessage())
                    .data(documentService.uploadDocument(file))
                    .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getAllIngredients(
            @RequestBody Filter filter
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DOCUMENT_SUCCESSFUL.getMessage())
                        .data(documentService.getAllDocument(filter))
                        .build()
        );
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable("id") Integer id) throws IOException {
        byte[] fileData = documentService.downloadDocument(id);
        DocumentDto dto = documentService.getDocument(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }
}
