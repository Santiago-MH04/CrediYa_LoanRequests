package co.com.powerup.crediya.santiagomh04.msvcloanrequests.usecase.loan;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.gateways.LoanRepository;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ILoanUseCaseTest {

    @Mock
    private LoanRepository repoLoan;

    @InjectMocks
    private ILoanUseCase loanUseCase;

    private Loan loan;
    private LoanType loanType;

    @BeforeEach
    void setUp() {
        this.loanType = LoanType.builder()
            .id(ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE))
            .name("Personal")
            .interestRate(BigDecimal.valueOf(0.15))
            .minimumBaseSalary(BigInteger.valueOf(2_000_000))
            .maximumDeadline(36)
            .build();

        this.loan = Loan.builder()
            .id(UUID.randomUUID())
            .identificationNumber("1234567890")
            .adjudicationDate(LocalDate.now())
            .deadline(24)
            .loanType(loanType)
            .amount(BigDecimal.valueOf(5_000_000))
            .status(Loan.LoanStatus.PENDING_OF_REVISION)
            .build();
    }

    @Test
    @DisplayName("It should return the loans acquired by an existing user")
    void findByClientIdentificationNumber_shouldReturnFluxOfLoans() {
        when(this.repoLoan.findByIdentificationNumber("1234567890"))
            .thenReturn(Flux.just(this.loan));

        StepVerifier.create(this.loanUseCase.findByClientIdentificationNumber("1234567890"))
            .expectNext(this.loan)
            .verifyComplete();

        verify(this.repoLoan).findByIdentificationNumber("1234567890");
    }

    @Test
    @DisplayName("It should return one loan, searched by its id")
    void findById_shouldReturnMonoOfLoan() {
        UUID id = this.loan.getId();
        when(this.repoLoan.findById(id)).thenReturn(Mono.just(loan));

        StepVerifier.create(this.loanUseCase.findById(id))
            .expectNext(this.loan)
            .verifyComplete();

        verify(this.repoLoan).findById(id);
    }

    @Test
    @DisplayName("It should return the loan request, after being persisted")
    void createLoan_shouldAssignPendingStatusAndPersist() {
        when(this.repoLoan.createLoan(any(Loan.class)))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(this.loanUseCase.createLoan(this.loan.toBuilder().status(Loan.LoanStatus.APPROVED).build()))
            .assertNext(saved -> assertEquals(Loan.LoanStatus.PENDING_OF_REVISION, saved.getStatus()))
            .verifyComplete();

        verify(this.repoLoan).createLoan(any(Loan.class));
    }

    @Test
    @DisplayName("It should update the status of a created loan request")
    void updateLoanStatus_shouldDelegateToRepository() {
        UUID id = this.loan.getId();
        Loan updated = this.loan.toBuilder().status(Loan.LoanStatus.APPROVED).build();

        when(this.repoLoan.updateLoanStatus(id, "APPROVED")).thenReturn(Mono.just(updated));

        StepVerifier.create(this.loanUseCase.updateLoanStatus(id, "APPROVED"))
            .expectNext(updated)
            .verifyComplete();

        verify(this.repoLoan).updateLoanStatus(id, "APPROVED");
    }

    @Test
    @DisplayName("It should return an empty Mono, meaning the loan request has been rejected")
    void updateLoanStatus_shouldReturnEmptyMonoWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(this.repoLoan.updateLoanStatus(id, "REJECTED")).thenReturn(Mono.empty());

        StepVerifier.create(this.loanUseCase.updateLoanStatus(id, "REJECTED"))
                .verifyComplete();

        verify(this.repoLoan).updateLoanStatus(id, "REJECTED");
    }
}