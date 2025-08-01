package doctorhoai.learn.authservice.config.cloudiary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudDinaryConfig {
    @Value("${spring.dinary.cloud.name}")
    private String CLOUD_NAME;
    @Value("${spring.dinary.api.key}")
    private String API_KEY;
    @Value("${spring.dinary.api.secret}")
    private String API_SECRET;

    @Bean
    public Cloudinary cloudPlatform(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);
    }
}

