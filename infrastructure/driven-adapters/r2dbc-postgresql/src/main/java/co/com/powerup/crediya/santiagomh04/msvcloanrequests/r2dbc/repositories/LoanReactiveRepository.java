package co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.repositories;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.entities.LoanEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface LoanReactiveRepository extends ReactiveCrudRepository<LoanEntity, UUID> {
    Flux<LoanEntity> findByIdentificationNumber(String identificationNumber);
}
