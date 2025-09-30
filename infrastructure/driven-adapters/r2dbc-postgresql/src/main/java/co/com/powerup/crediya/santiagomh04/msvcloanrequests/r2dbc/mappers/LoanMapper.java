package co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.mappers;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.gateways.LoanTypeRepository;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.entities.LoanEntity;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoanMapper {

    private final ObjectMapper objectMapper;
    private final LoanTypeRepository repoLoanType;

    public Mono<Loan> toDomain(LoanEntity loanEntity){
        Loan loan = this.objectMapper.map(loanEntity, Loan.class);

        return this.repoLoanType.findById(loanEntity.getLoanTypeId())
            .flatMap(loanTypeEntity -> {
                LoanType loanType = this.objectMapper.map(loanTypeEntity, LoanType.class);
                loan.setLoanType(loanType);
                return Mono.just(loan);
            });
    }

    public Mono<LoanEntity> toEntity(Loan loan) {
        String loanTypeName = loan.getLoanType().getName();

        // Completing the LoanType from partial mapping from entry-points layer
        return this.repoLoanType.findByName(loanTypeName)
            .map(fullLoanTypeEntity -> {
                LoanEntity loanEntity = this.objectMapper.map(loan, LoanEntity.class);
                    loanEntity.setLoanTypeId(fullLoanTypeEntity.getId());
                return loanEntity;
            });
    }
}
