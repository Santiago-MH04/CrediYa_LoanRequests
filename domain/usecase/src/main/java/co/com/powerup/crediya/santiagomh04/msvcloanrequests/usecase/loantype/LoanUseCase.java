package co.com.powerup.crediya.santiagomh04.msvcloanrequests.usecase.loantype;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanUseCase {
    Mono<LoanType> createLoanType(LoanType loanType);
    Flux<LoanType> findAll();
}
