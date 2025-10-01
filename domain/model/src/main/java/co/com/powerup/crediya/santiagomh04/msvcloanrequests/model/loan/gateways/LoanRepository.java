package co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.gateways;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanRepository {
    Mono<Loan> createLoan(Loan loan);
    Mono<Loan> updateLoanStatus(UUID loanId, String newStatus);
    Mono<Loan> findById(UUID id);
    Flux<Loan> findByIdentificationNumber(String identificationNumber);
}
