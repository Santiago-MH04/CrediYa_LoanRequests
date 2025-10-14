package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.config;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.RouterRest;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.handlers.apiHandler.LoanHandler;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.handlers.loggingHelpers.HandlerLoggingSupport;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.mappers.LoanApiMapper;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.LoanValidator;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.usecase.loan.LoanUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mockito.Mockito.mock;

@TestConfiguration
/*@Configuration*/
public class LoanRouterTestConfig {
    @Bean
    public LoanUseCase loanUseCase() {
        return mock(LoanUseCase.class);
    }

    @Bean
    public LoanApiMapper loanApiMapper() {
        return mock(LoanApiMapper.class);
    }

    @Bean
    public LoanValidator loanValidator() {
        return mock(LoanValidator.class);
    }

    @Bean
    public HandlerLoggingSupport handlerLoggingSupport() {
        return new HandlerLoggingSupport();
        /*return mock(HandlerLoggingSupport.class);*/
    }

    @Bean
    public LoanHandler loanHandler(
        LoanUseCase loanUseCase,
        LoanApiMapper loanApiMapper,
        LoanValidator loanValidator,
        HandlerLoggingSupport loggingSupport
    ) {
        return new LoanHandler(loanUseCase, loanApiMapper, loanValidator, loggingSupport);
    }

    @Bean
    public RouterRest routerRest(LoanHandler loanHandler) {
        return new RouterRest(loanHandler);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(RouterRest routerRest) {
        return routerRest.routerFunction();
    }
}
