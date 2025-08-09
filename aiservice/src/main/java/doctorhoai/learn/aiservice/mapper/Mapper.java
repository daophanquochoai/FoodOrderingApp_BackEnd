package doctorhoai.learn.aiservice.mapper;

import doctorhoai.learn.aiservice.dto.DocumentDto;
import doctorhoai.learn.aiservice.model.Document;
import org.springframework.stereotype.Service;

@Service
public class Mapper {
    public DocumentDto convertToDocumentDto(Document document) {
        return DocumentDto.builder()
                .id(document.getId())
                .name(document.getName())
                .size(document.getSize())
                .url(document.getUrl())
                .isActive(document.getIsActive())
                .createdAt(document.getCreatedAt())
                .build();
    }

    public Document convertToDocument(DocumentDto documentDto) {
        return Document.builder()
                .name(documentDto.getName())
                .size(documentDto.getSize())
                .url(documentDto.getUrl())
                .isActive(documentDto.getIsActive())
                .createdAt(documentDto.getCreatedAt())
                .build();
    }
}
