package co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.gateways;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.utils.LoanStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanRepository {
    Mono<Loan> createLoan(Loan loan);
    Mono<Loan> updateLoanStatus(UUID loanId, LoanStatus newStatus);
    Flux<Loan> findByIdentificationNumber(String identificationNumber);
}
