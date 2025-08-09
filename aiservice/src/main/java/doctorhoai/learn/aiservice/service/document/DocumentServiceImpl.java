package doctorhoai.learn.aiservice.service.document;


import doctorhoai.learn.aiservice.dto.DocumentDto;
import doctorhoai.learn.aiservice.dto.PageObject;
import doctorhoai.learn.aiservice.dto.filter.Filter;
import doctorhoai.learn.aiservice.mapper.Mapper;
import doctorhoai.learn.aiservice.model.Document;
import doctorhoai.learn.aiservice.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
    private final DocumentRepository documentRepository;
    private final Mapper mapper;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public DocumentDto uploadDocument(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        File dir = new File(uploadDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Could not create upload directory: " + uploadDir);
        }

        String originalFilename = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, originalFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Document document = Document.builder()
                .name(originalFilename)
                .size(file.getSize())
                .url("/uploads/" + originalFilename)
                .isActive(true)
                .createdAt(java.time.LocalDateTime.now())
                .createBy(null)
                .build();

        Document saved = documentRepository.save(document);
        return mapper.convertToDocumentDto(saved);
    }

    @Override
    public DocumentDto getDocument(Integer id) {
        Optional<Document> doc = documentRepository.findById(id);
        return doc.map(mapper::convertToDocumentDto).orElse(null);
    }

    @Override
    public PageObject getAllDocument(Filter filter) {
        logger.info("Filter: {}", filter);
        Pageable pageable = PageRequest.of(filter.getPageNo() - 1, filter.getPageSize());
        Page<Document> page = documentRepository.findAll(pageable);
        return PageObject.builder()
                .page(page.getNumber() + 1) // trang hiện tại (1-based)
                .totalPage(page.getTotalPages()) // tổng số trang
                .data(page.getContent().stream().map(mapper::convertToDocumentDto).toList())
                .build();
    }

    @Override
    public byte[] downloadDocument(Integer id) throws IOException {
        Optional<Document> docOpt = documentRepository.findById(id);
        if (docOpt.isEmpty()) {
            throw new IllegalArgumentException("Document not found");
        }
        String url = docOpt.get().getUrl();
        String fileName = docOpt.get().getName();

        // Bỏ dấu "/" ở đầu nếu có
        if (url.startsWith("/")) {
            url = url.substring(1);
        }

        // Lấy thư mục chứa file (không lấy tên file)
        String dirPath = url.substring(0, url.lastIndexOf('/'));

        Path filePath = Paths.get(dirPath, fileName);

        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filePath);
        }
        return Files.readAllBytes(filePath);
    }
}

