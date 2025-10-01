package co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.adapters;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.gateways.LoanRepository;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.entities.LoanEntity;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.mappers.LoanMapper;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.repositories.LoanReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LoanReactiveRepositoryAdapter implements LoanRepository {

    private final LoanReactiveRepository repoLoanReactive;
    private final LoanMapper loanMapper;

    @Override
    public Flux<Loan> findByIdentificationNumber(String identificationNumber) {
        return this.repoLoanReactive.findByIdentificationNumber(identificationNumber)
            .flatMap(this.loanMapper::toDomain);
    }

    @Override
    public Mono<Loan> findById(UUID id) {
        return this.repoLoanReactive.findById(id)
            .flatMap(this.loanMapper::toDomain);
    }

    @Override
    public Mono<Loan> createLoan(Loan loan) {
        return this.loanMapper.toEntity(loan)
            .flatMap(this.repoLoanReactive::save)
            .flatMap(this.loanMapper::toDomain);
    }

    @Override
    public Mono<Loan> updateLoanStatus(UUID loanId, String newStatus) {
        return this.repoLoanReactive.findById(loanId)
        .flatMap(le -> {
            le.setStatus(LoanEntity.LoanStatus.valueOf(newStatus)/*.name()*/);
            return this.repoLoanReactive.save(le);
        })
        .flatMap(this.loanMapper::toDomain);
    }

}
