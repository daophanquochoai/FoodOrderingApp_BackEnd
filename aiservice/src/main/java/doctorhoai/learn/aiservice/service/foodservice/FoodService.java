package doctorhoai.learn.aiservice.service.foodservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.agent.tool.Tool;
import doctorhoai.learn.aiservice.dto.FoodDto;
import doctorhoai.learn.aiservice.feign.foodservice.FoodFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodFeign foodFeign;

    @Tool("Khi khách hàng hỏi về món ăn")
    public List<FoodDto> getAllFood(){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseEntity<ResponseObject> response = foodFeign.getAllFoodNoFilter();
            List<FoodDto> foodDtos = objectMapper.convertValue(response.getBody().getData(), new TypeReference<List<FoodDto>>(){});
            return foodDtos;
        }catch (Exception e){
            return null;
        }
    }
}
