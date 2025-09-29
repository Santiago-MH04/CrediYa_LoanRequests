package co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.gateways;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<LoanType> createLoanType(LoanType loanType);
    Flux<LoanType> findAll();
    Mono<LoanType> findById(Long id);
    Mono<LoanType> findByName(String name);
}
