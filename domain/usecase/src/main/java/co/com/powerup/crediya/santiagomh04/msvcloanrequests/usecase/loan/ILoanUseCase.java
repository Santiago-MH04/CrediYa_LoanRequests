package co.com.powerup.crediya.santiagomh04.msvcloanrequests.usecase.loan;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.gateways.LoanRepository;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.utils.LoanStatus;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class ILoanUseCase implements LoanUseCase{

    private final LoanRepository repoLoan;

    @Override
    public Mono<Loan> create(Loan loan) {
        return this.repoLoan.createLoan(loan);
    }

    @Override
    public Mono<Loan> updateLoanStatus(UUID loanId, LoanStatus newStatus) {
        return this.repoLoan.updateLoanStatus(loanId, newStatus);
    }

    @Override
    public Flux<Loan> findByClientIdentificationNumber(String identificationNumber) {
        return null;
    }
}
