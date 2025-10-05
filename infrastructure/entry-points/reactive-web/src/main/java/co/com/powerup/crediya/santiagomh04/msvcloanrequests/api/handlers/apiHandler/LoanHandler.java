package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.handlers.apiHandler;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanStatusDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.mappers.LoanApiMapper;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.LoanValidator;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.usecase.loan.LoanUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoanHandler {

    private final LoanUseCase loanUseCase;
    private final LoanApiMapper loanApiMapper;

    private final LoanValidator loanValidator;

    public Mono<ServerResponse> listenGETUseCaseFindAllByIdentificationNumber(ServerRequest serverRequest) {
        String idNumber = serverRequest.pathVariable("identificationNumber");

        return this.loanValidator.validateUserExists(idNumber) // Mono<Void>
            .thenMany(this.loanUseCase.findByClientIdentificationNumber(idNumber)) // Flux<Loan>
            .map(this.loanApiMapper::toResponse)
            .collectList()
            .flatMap(loanResponseDTOList ->
                ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loanResponseDTOList)
            );
    }

    public Mono<ServerResponse> listenGETUseCaseFindById(ServerRequest serverRequest) {
        UUID id = UUID.fromString(serverRequest.pathVariable("id"));

        return this.loanValidator.validateLoanExistence(id)
            .map(this.loanApiMapper::toResponse)
            .flatMap(loanResponseDTO ->
                ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loanResponseDTO)
            );
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoanRequestDTO.class)
            .flatMap(this.loanValidator::validateLoanRequest)
            .map(this.loanApiMapper::toDomain)
            .flatMap(this.loanUseCase::createLoan)
            .map(this.loanApiMapper::toResponse)
            .flatMap(loanResponseDTO ->
                ServerResponse.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(loanResponseDTO)
            );
    }

    public Mono<ServerResponse> listenPATCHUseCase(ServerRequest serverRequest) {
        UUID loanId = UUID.fromString(serverRequest.pathVariable("id"));

        return serverRequest.bodyToMono(LoanStatusDTO.class)
            .flatMap(dto -> this.loanUseCase.updateLoanStatus(loanId, dto.status().toUpperCase()))
            .map(this.loanApiMapper::toResponse)
            .flatMap(loanResponseDTO ->
                ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loanResponseDTO)
            );
    }
}
