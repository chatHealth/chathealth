package chathealth.chathealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChathealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChathealthApplication.class, args);
    }

}
