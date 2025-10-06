package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.ErrorResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanStatusDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.handlers.apiHandler.LoanHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
            path = "/api/v1/requests/{identificationNumber}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET,
            beanClass = LoanHandler.class,
            beanMethod = "listenGETUseCaseFindAllByIdentificationNumber",
            operation = @Operation(
                operationId = "getAllLoansByUserIdentificationNumber",
                summary = "Finds all loans by user’s identification number",
                description = "Shows the historic of loans requested by a user",
                tags = {"Loans"},
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Loan requests by user",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = LoanRequestDTO.class)))
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/api/v1/requests/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET,
            beanClass = LoanHandler.class,
            beanMethod = "listenGETUseCaseFindById",
            operation = @Operation(
                operationId = "findLoanRequest",
                summary = "Finds a loan request",
                description = "Fetches a loan request using its id",
                tags = {"Loan"},
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Loan request found successfully",
                        content = @Content(schema = @Schema(implementation = LoanRequestDTO.class))
                    ),
                    @ApiResponse(
                        responseCode = "404",
                        description = "Loan not found",
                        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/api/v1/requests",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST,
            beanClass = LoanHandler.class,
            beanMethod = "listenPOSTUseCase",
            operation = @Operation(
                operationId = "registerLoanRequest",
                summary = "Register a new loan request",
                description = "Registers a new loan request with the information provided",
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
        @RouterOperation(
            path = "/api/v1/requests/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.PATCH,
            beanClass = LoanHandler.class,
            beanMethod = "listenPATCHUseCase",
            operation = @Operation(
                operationId = "updateLoanRequestStatus",
                summary = "Updates a loan request status",
                description = "Updates an already registered loan status, either to approved or rejected",
                tags = {"Loan"},
                requestBody = @RequestBody(
                    required = true,
                    description = "Loan status",
                    content = @Content(schema = @Schema(implementation = LoanStatusDTO.class))
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Loan request status successfully updated",
                        content = @Content(schema = @Schema(implementation = LoanRequestDTO.class))
                    ),
                    @ApiResponse(
                        responseCode = "404",
                        description = "Loan request not found",
                        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
                }
            )
        )
        /*@RouterOperation()*/
    })
    public RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/api/v1/requests/{identificationNumber}"), this.loanHandler::listenGETUseCaseFindAllByIdentificationNumber)
            .and(route(GET("/api/v1/requests/{id}"), this.loanHandler::listenGETUseCaseFindById))
            .andRoute(POST("/api/v1/requests"), this.loanHandler::listenPOSTUseCase)
            .andRoute(PATCH("/api/v1/requests/{id}"), this.loanHandler::listenPATCHUseCase);
    }
}
