package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.config;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.handlers.apiHandler.LoanHandler;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.RouterRest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@ContextConfiguration(classes = {/*RouterRest.class, LoanHandler.class*/ LoanRouterTestConfig.class, ConfigTest.DummyRouter.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @TestConfiguration
    static class DummyRouter {
        @Bean
        RouterFunction<ServerResponse> testRoute() {
            return RouterFunctions.route(GET("/test"),
                request -> ServerResponse.ok().build());
        }
    }

    @Test
    void corsConfigurationShouldAllowOrigins() {
        this.webTestClient.get()
            .uri("/test")
                /*.uri("/api/v1/requests/{id}", UUID.randomUUID())
                .uri("/api/v1/requests")
                .uri("/api/usecase/path")*/
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Security-Policy",
                    "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
            .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
            .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
            .expectHeader().valueEquals("Server", "")
            .expectHeader().valueEquals("Cache-Control", "no-store")
            .expectHeader().valueEquals("Pragma", "no-cache")
            .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }
        /*@Test
        void corsConfigurationShouldAllowOrigins() {
            webTestClient.get()
                    .uri("/test")
                    .exchange()
                    .expectStatus().isOk();
        }*/
}