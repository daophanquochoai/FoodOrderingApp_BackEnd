package doctorhoai.learn.aiservice.repository;

import doctorhoai.learn.aiservice.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
