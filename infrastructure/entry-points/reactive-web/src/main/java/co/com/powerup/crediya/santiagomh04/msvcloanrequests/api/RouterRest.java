package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.handlers.LoanHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final LoanHandler loanHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(/*LoanHandler handler*/) {
        return route(GET("/api/v1/requests/{identificationNumber}"), this.loanHandler::listenGETUseCaseFindAllByIdentificationNumber)
            .and(route(GET("/api/v1/requests/{id}"), this.loanHandler::listenGETUseCaseFindById))
            .andRoute(POST("/api/v1/requests"), this.loanHandler::listenPOSTUseCase)
            .andRoute(PATCH("/api/v1/requests/{id}"), this.loanHandler::listenPATCHUseCase);
    }
}
