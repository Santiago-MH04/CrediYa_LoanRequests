package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.businessLogic.BusinessLogicCauses;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.businessLogic.BusinessLogicException;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.gateway.BadGatewayException;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.gateway.GatewayCauses;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.requestBody.RequestBodyCauses;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.requestBody.RequestBodyException;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.gateways.LoanRepository;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class LoanValidator {
    private final LoanRepository repoLoan;
    private final LoanTypeRepository repoLoanType;
    private final WebClient authenticationWebClient;

    public Mono<LoanRequestDTO> validateLoanRequest(LoanRequestDTO loanRequestDTO) {
        return validateRequiredFields(loanRequestDTO)
            .then(
                validateUserExistsAndExtractSalary(loanRequestDTO.identificationNumber())
                .zipWith(validateLoanTypeRequested(loanRequestDTO.loanTypeName()))
            )
            .flatMap(tuple -> {
                BigInteger userBaseSalary = tuple.getT1();
                LoanType loanType = tuple.getT2();

                return validateUsersBaseSalary(userBaseSalary, loanType)
                    .then(validateDeadlineRequested(loanRequestDTO.deadline(), loanType));
            })
            .thenReturn(loanRequestDTO);
    }

    public Mono<Loan> validateLoanExistence(UUID loanId) {
        return this.repoLoan.findById(loanId)
            .switchIfEmpty(Mono.error(new BusinessLogicException(
                RequestBodyCauses.INVALID_LOAN_TYPE_ERROR.getMessage()
            )));
    }

    public Mono<Void> validateUserExists(String identificationNumber) {
        return this.authenticationWebClient.get()
            .uri("api/v1/users/{identificationNumber}", identificationNumber)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                Mono.error(new BusinessLogicException(
                    BusinessLogicCauses.USER_NOT_FOUND_ERROR.getMessage()
                ))
            )
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                Mono.error(new BadGatewayException(
                    GatewayCauses.REMOTE_SERVICE_UNAVAILABLE_ERROR.getMessage()
                ))
            )
            .bodyToMono(Map.class)
            .then();
    }


    // 1. Required fields validation
    private Mono<Void> validateRequiredFields(LoanRequestDTO loanRequestDTO) {
        String missingFields = Stream.of(
                getMissingFieldName(loanRequestDTO.identificationNumber(), "identificationNumber"),
                getMissingFieldName(loanRequestDTO.adjudicationDate(), "adjudicationDate"),
                getMissingFieldName(loanRequestDTO.deadline(), "deadline"),
                getMissingFieldName(loanRequestDTO.loanTypeName(), "loanTypeName"),
                getMissingFieldName(loanRequestDTO.amount(), "amount")
            )
            .filter(Objects::nonNull)
            .collect(Collectors.joining(", "));

        if (!missingFields.isEmpty()){
            String errorMessage = ("(%s): %s").formatted(missingFields, RequestBodyCauses.EMPTY_FIELD_ERROR.getMessage());
            return Mono.error(new RequestBodyException(errorMessage));
        }

        return Mono.empty();
    }

    // 2. Validate user existence and extract salary
    private Mono<BigInteger> validateUserExistsAndExtractSalary(String identificationNumber) {
        return this.authenticationWebClient.get()
            .uri("api/v1/users/{identificationNumber}", identificationNumber)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                Mono.error(new BusinessLogicException(
                    BusinessLogicCauses.USER_NOT_FOUND_ERROR.getMessage()
                ))
            )
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                Mono.error(new BadGatewayException(
                    GatewayCauses.REMOTE_SERVICE_UNAVAILABLE_ERROR.getMessage()
                ))
            )
            .bodyToMono(Map.class)
            .map(user -> new BigInteger(user.get("baseSalary").toString()));
    }


    // 3. Validate loan type requested
    private Mono<LoanType> validateLoanTypeRequested(String loanTypeName) {
        return this.repoLoanType.findByName(loanTypeName)
            .switchIfEmpty(Mono.error(new BusinessLogicException(
                RequestBodyCauses.INVALID_LOAN_TYPE_ERROR.getMessage()
            )));
    }

    // 4. Validate user’s base salary
    private Mono<Void> validateUsersBaseSalary(BigInteger userBaseSalary, LoanType loanType) {
        boolean invalidSalary = (userBaseSalary == null) ||
            (userBaseSalary.compareTo(loanType.getMinimumBaseSalary()) < 0);

        return invalidSalary ?
            Mono.error(new BusinessLogicException(
                RequestBodyCauses.INSUFFICIENT_BASE_SALARY_ERROR.getMessage()))
            :
            Mono.empty();
    }


    // 5. Validate deadline requested
    private Mono<Void> validateDeadlineRequested(int deadline, LoanType loanType) {
        boolean deadlineOutOfRange = (loanType.getMaximumDeadline() != 0) &&
            (deadline > loanType.getMaximumDeadline());

        return deadlineOutOfRange ?
            Mono.error(new BusinessLogicException(
                RequestBodyCauses.DEADLINE_OUT_OF_RANGE_ERROR.getMessage()))
            :
            Mono.empty();
    }

    private String getMissingFieldName(Object value, String fieldName) {
        return (value == null || (value instanceof String && ((String) value).isBlank())) ?
            fieldName
            :
            null;
    }
}
