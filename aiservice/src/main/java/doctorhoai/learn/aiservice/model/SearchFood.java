package doctorhoai.learn.aiservice.model;


import doctorhoai.learn.aiservice.model.convert.FloatArrayVectorConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "search_food")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class SearchFood {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    private Integer foodId;
    @Column(name = "embedding", columnDefinition = "vector(768)")
    private String embedding;
}
