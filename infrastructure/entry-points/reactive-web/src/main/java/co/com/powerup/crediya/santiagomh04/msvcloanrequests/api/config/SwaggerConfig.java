package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservice loan requests")
                .version("v1.0")
                .description("Loan requests creation microservice with centralised logging and reactive handler support")
            );
    }
}
