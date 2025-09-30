package co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.repositories;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.entities.LoanTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface LoanTypeReactiveRepository extends ReactiveCrudRepository<LoanTypeEntity, Long> {
    Mono<LoanTypeEntity> findByName(String name);
}
