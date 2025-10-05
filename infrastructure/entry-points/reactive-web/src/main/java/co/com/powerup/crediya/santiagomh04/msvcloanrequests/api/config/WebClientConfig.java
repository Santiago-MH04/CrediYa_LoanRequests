package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "config.baseurl.endpoint")
public class WebClientConfig {

    private String authenticationBaseUrl;

    @Bean
    public WebClient authenticationWebClient(WebClient.Builder builder) {
        return builder
            .baseUrl(this.authenticationBaseUrl)
            .build();
    }
}
