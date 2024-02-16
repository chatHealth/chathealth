package chathealth.chathealth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 설정 파일에 있는 환경 변수 불러오기
    @Value("${file.path}")
    private String path;

    @Bean
    MappingJackson2JsonView jsonView(){
        return new MappingJackson2JsonView();
    }



    // 파일 경로 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profile/**")
                .addResourceLocations("file:///"+path);

        registry.addResourceHandler("/chat/**")
                .addResourceLocations("file:///" + path + "/chat/");
    }
}
