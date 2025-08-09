package doctorhoai.learn.aiservice.service.document;

import doctorhoai.learn.aiservice.dto.DocumentDto;
import doctorhoai.learn.aiservice.dto.PageObject;
import doctorhoai.learn.aiservice.dto.filter.Filter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {
    PageObject getAllDocument(Filter filter);
    DocumentDto uploadDocument(MultipartFile file) throws IOException;
    byte[] downloadDocument(Integer id) throws IOException;
    DocumentDto getDocument(Integer id);
}

