package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.ErrorResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.handlers.apiHandler.LoanHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final LoanHandler loanHandler;

    @Bean
    @RouterOperations({
        @RouterOperation(
            path = "/api/v1/requests",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST,
            beanClass = LoanHandler.class,
            beanMethod = "listenPOSTUseCase",
            operation = @Operation(
                operationId = "registerLoanRequest",
                summary = "Register a new loan request",
                description = "Registers a new loan request with the information provided.",
                tags = {"Loan"},
                requestBody = @RequestBody(
                    required = true,
                    description = "Loan registration request",
                    content = @Content(schema = @Schema(implementation = LoanRequestDTO.class))
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "201",
                        description = "Loan request registered successfully",
                        content = @Content(schema = @Schema(implementation = LoanRequestDTO.class))
                    ),
                    @ApiResponse(
                        responseCode = "404",
                        description = "Lending user not found",
                        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
                }
            )
        ),
        /*@RouterOperation()*/
    })
    public RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/api/v1/requests/{identificationNumber}"), this.loanHandler::listenGETUseCaseFindAllByIdentificationNumber)
            .and(route(GET("/api/v1/requests/{id}"), this.loanHandler::listenGETUseCaseFindById))
            .andRoute(POST("/api/v1/requests"), this.loanHandler::listenPOSTUseCase)
            .andRoute(PATCH("/api/v1/requests/{id}"), this.loanHandler::listenPATCHUseCase);
    }
}
