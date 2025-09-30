package co.com.powerup.crediya.santiagomh04.msvcloanrequests.usecase.loan;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanUseCase {
    Mono<Loan> create(Loan loan);
    Mono<Loan> updateLoanStatus(UUID loanId, String newStatus);
    Flux<Loan> findByClientIdentificationNumber(String identificationNumber);
}
