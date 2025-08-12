package doctorhoai.learn.aiservice.service.semantic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import doctorhoai.learn.aiservice.dto.FoodDto;
import doctorhoai.learn.aiservice.feign.foodservice.FoodFeign;
import doctorhoai.learn.aiservice.model.SearchFood;
import doctorhoai.learn.aiservice.repository.SearchFoodRepository;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchFoodServiceImpl implements SearchFoodService{

    private final SearchFoodRepository searchFoodRepository;
    private final EmbeddingModel embeddingModel;
    @Autowired
    private EntityManager entityManager;
    private final FoodFeign foodFeign;

    @Override
    public void addFood(FoodDto dto) {
        String content = getFoodContent(dto);

        Embedding embedding = embeddingModel.embed(content).content();
        String vectorText = Arrays.toString(embedding.vector())
                .replace("[", "[")
                .replace("]", "]");
        String sql = "INSERT INTO search_food (embedding, food_id) VALUES (?::vector, ?) ON CONFLICT (food_id) DO UPDATE SET embedding = EXCLUDED.embedding";
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setObject(1, vectorText);
                ps.setInt(2, dto.getId());
                ps.executeUpdate();
            }
        });
    }

    private String getFoodContent(FoodDto dto) {
        return dto.getName() + " "
                + dto.getDesc() + " "
                + (dto.getCategory() != null ? dto.getCategory().getName() + " " : "")
                + (dto.getFoodSizes() != null ? dto.getFoodSizes().stream()
                .map(size -> size.getPrice() + " " + size.getReadyInMinutes())
                .collect(Collectors.joining(" ")) : "");
    }

    @Override
    public void removeFood(FoodDto dto) {
        Optional<SearchFood> searchFoodOptional = searchFoodRepository.findByFoodId(dto.getId());
        searchFoodOptional.ifPresent(searchFoodRepository::delete);
    }

    @Override
    public void updateFood(FoodDto dto) {
        Optional<SearchFood> searchFoodOptional = searchFoodRepository.findByFoodId(dto.getId());
        if(searchFoodOptional.isEmpty()) {
            return;
        }
        String content = getFoodContent(dto);

        Embedding embedding = embeddingModel.embed(content).content();
        SearchFood searchFood = searchFoodOptional.get();
        searchFood.setEmbedding(Arrays.toString(embedding.vector()));
        searchFoodRepository.save(searchFood);
    }

    @Override
    public List<Integer> searchFood(String search) {
        String prompt = "Tìm món ăn liên quan đến: " + search;
        Embedding embedding = embeddingModel.embed(prompt).content();

        return searchFoodRepository.searchSimilarFoods(embedding.vector(),0.3f, 100).stream().map(SearchFood::getFoodId).toList();
    }

    @Override
    public List<FoodDto> searchFoodDto(String search) {
        String prompt = "Tìm món ăn liên quan đến: " + search;
        Embedding embedding = embeddingModel.embed(prompt).content();

        List<Integer> ids = searchFoodRepository.searchSimilarFoods(embedding.vector(),0.3f, 100).stream().map(SearchFood::getFoodId).toList();
        return getFoodsByIds(ids);
    }

    public List<FoodDto> getFoodsByIds(List<Integer> ids) {
        ObjectMapper mapper = new ObjectMapper();
        List<FoodDto> food = new ArrayList<>();
        ResponseEntity<ResponseObject> response = foodFeign.getAllIdsFood(ids);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            ResponseObject body = response.getBody();
            if (body.getData() != null) {
                food = mapper.convertValue(body.getData(), new TypeReference<List<FoodDto>>() {});
            }
        }
        return food;
    }
}
