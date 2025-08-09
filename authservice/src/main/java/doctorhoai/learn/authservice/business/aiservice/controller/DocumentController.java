package doctorhoai.learn.authservice.business.aiservice.controller;

import doctorhoai.learn.authservice.business.aiservice.model.Filter;
import doctorhoai.learn.authservice.business.aiservice.service.document.DocumentFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentFeign documentFeign;
    @PostMapping("/upload")
    ResponseEntity<ResponseObject> uploadDocument(
            @RequestParam("file") MultipartFile file) throws IOException{
        return documentFeign.uploadDocument(file);
    }
    @PostMapping("/all")
    ResponseEntity<ResponseObject> getAllIngredients(
            @RequestBody Filter filter
    ){
        return documentFeign.getAllIngredients(filter);
    }

    @GetMapping("/download/{id}")
    ResponseEntity<byte[]> downloadDocument(@PathVariable("id") Integer id) throws IOException{
        return documentFeign.downloadDocument(id);
    }

}
