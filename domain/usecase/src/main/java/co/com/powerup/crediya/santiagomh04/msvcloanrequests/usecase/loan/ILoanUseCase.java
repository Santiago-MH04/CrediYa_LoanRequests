package co.com.powerup.crediya.santiagomh04.msvcloanrequests.usecase.loan;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class ILoanUseCase implements LoanUseCase{

    private final LoanRepository repoLoan;

    @Override
    public Mono<Loan> create(Loan loan) {
        //Assign status PENDING_OF_REVISION by default
        loan.setStatus(Loan.LoanStatus.PENDING_OF_REVISION.name());
        return this.repoLoan.createLoan(loan);
    }

    @Override
    public Mono<Loan> updateLoanStatus(UUID loanId, String newStatus) {
        return this.repoLoan.updateLoanStatus(loanId, newStatus);
    }

    @Override
    public Flux<Loan> findByClientIdentificationNumber(String identificationNumber) {
        return null;
    }
}
