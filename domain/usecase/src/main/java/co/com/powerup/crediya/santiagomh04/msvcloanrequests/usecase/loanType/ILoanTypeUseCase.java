package co.com.powerup.crediya.santiagomh04.msvcloanrequests.usecase.loanType;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ILoanTypeUseCase implements LoanTypeUseCase {

    private final LoanTypeRepository repoLoanType;

    @Override
    public Mono<LoanType> createLoanType(LoanType loanType) {
        return this.repoLoanType.createLoanType(loanType);
    }

    @Override
    public Flux<LoanType> findAll() {
        return this.repoLoanType.findAll();
    }

    @Override
    public Mono<LoanType> findById(Long id) {
        return this.repoLoanType.findById(id);
    }

    @Override
    public Mono<LoanType> findByName(String name) {
        return this.repoLoanType.findByName(name);
    }
}
