package co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.adapters;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.gateways.LoanTypeRepository;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.mappers.LoanTypeMapper;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.repositories.LoanTypeReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoanTypeReactiveRepositoryAdapter implements LoanTypeRepository {

    private final LoanTypeReactiveRepository repoLoanType;
    private final LoanTypeMapper loanTypeMapper;

    @Override
    public Mono<LoanType> createLoanType(LoanType loanType) {
        return this.repoLoanType.save(this.loanTypeMapper.toEntity(loanType))
            .map(this.loanTypeMapper::toDomain);
    }

    @Override
    public Flux<LoanType> findAll() {
        return this.repoLoanType.findAll()
            .map(this.loanTypeMapper::toDomain);
    }

    @Override
    public Mono<LoanType> findById(Long id) {
        return this.repoLoanType.findById(id)
            .map(this.loanTypeMapper::toDomain);
    }

    @Override
    public Mono<LoanType> findByName(String name) {
        return this.repoLoanType.findByName(name)
            .map(this.loanTypeMapper::toDomain);
    }
}
