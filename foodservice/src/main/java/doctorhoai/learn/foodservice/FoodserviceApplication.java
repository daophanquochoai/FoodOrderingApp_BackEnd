package doctorhoai.learn.foodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class FoodserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodserviceApplication.class, args);
    }

}
