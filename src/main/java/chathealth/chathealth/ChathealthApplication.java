package chathealth.chathealth;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class ChathealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChathealthApplication.class, args);
    }

    // timezone 설정
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
    }
}
