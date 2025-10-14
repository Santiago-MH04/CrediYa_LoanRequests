package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.config.LoanRouterTestConfig;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanTypeDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.handlers.apiHandler.LoanHandler;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.handlers.loggingHelpers.HandlerLoggingSupport;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.mappers.LoanApiMapper;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.LoanValidator;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.usecase.loan.LoanUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/*@ContextConfiguration(classes = {RouterRest.class, LoanHandler.class})*/
/*@WebFluxTest(RouterRest.class)
@Import(LoanRouterTestConfig.class)*/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LoanRouterTestConfig.class})
class RouterRestTest {

    /*@Autowired
    private WebTestClient webTestClient;*/
    private WebTestClient webTestClient;

    @Autowired
    private LoanUseCase loanUseCase;

    @Autowired
    private LoanApiMapper loanApiMapper;

    @Autowired
    private LoanValidator loanValidator;

    @Autowired
    private HandlerLoggingSupport handlerLoggingSupport;

    @Autowired
    private LoanHandler loanHandler;

    @Autowired
    private RouterRest routerRest;

    @Autowired
    private RouterFunction<ServerResponse> routerFunction;

    private Loan loan;
    private LoanType loanType;
    private LoanRequestDTO loanRequestDTO;
    private LoanResponseDTO loanResponseDTO;
    private LoanTypeDTO loanTypeDTO;

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

        this.loanTypeDTO = new LoanTypeDTO(
            this.loanType.getId(),
            this.loanType.getName(),
            BigDecimal.valueOf(0.15),
            BigInteger.valueOf(2),
            24
        );

        this.loanRequestDTO = new LoanRequestDTO(
            this.loan.getIdentificationNumber(),
            this.loan.getAdjudicationDate(),
            this.loan.getDeadline(),
            this.loan.getLoanType().getName(),
            this.loan.getAmount()
        );

        this.loanResponseDTO = new LoanResponseDTO(
            this.loan.getId(),
            this.loan.getIdentificationNumber(),
            LocalDate.now(),
            12,
            this.loanTypeDTO,
            this.loan.getAmount(),
            loan.getStatus().name()
        );
        when(this.loanUseCase.findById(any(UUID.class))).thenReturn(Mono.just(this.loan));
        this.webTestClient = WebTestClient.bindToRouterFunction(this.routerFunction).build();
    }

    // --- GET /api/v1/requests/{identificationNumber} ---
    @Test
    void shouldReturnLoansByIdentificationNumber() {

        when(this.loanValidator.validateUserExists("123456")).thenReturn(Mono.empty());
        when(this.loanUseCase.findByClientIdentificationNumber("123456")).thenReturn(Flux.just(this.loan));
        when(this.loanApiMapper.toResponse(this.loan)).thenReturn(this.loanResponseDTO);

        this.webTestClient.get()
            .uri("/api/v1/requests/{identificationNumber}", "123456")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$[0].status").isEqualTo("PENDING_OF_REVISION");
    }

    // --- GET /api/v1/requests/{id} ---
    @Test
    void shouldReturnLoanById() {
        when(this.loanValidator.validateLoanExistence(this.loan.getId())).thenReturn(Mono.just(this.loan));
        /*when(this.loanUseCase.findById(this.loan.getId())).thenReturn(Mono.just(this.loan));*/
        when(this.loanApiMapper.toResponse(this.loan)).thenReturn(this.loanResponseDTO);

        this.webTestClient.get()
            .uri("/api/v1/requests/single/{id}", this.loan.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(this.loan.getId().toString())
            .jsonPath("$.status").isEqualTo("PENDING_OF_REVISION");
    }

    // --- POST /api/v1/requests ---
    @Test
    void shouldCreateLoan() {
        Loan createdLoan = Loan.builder()
            .id(UUID.randomUUID())
            .identificationNumber("1234567890")
            .adjudicationDate(LocalDate.of(2025, 10, 8))
            .deadline(24)
            .loanType(this.loanType) // o el que uses en tu test
            .amount(BigDecimal.valueOf(5_000_000L))
            .status(Loan.LoanStatus.PENDING_OF_REVISION)
            .build();


        when(this.loanValidator.validateLoanRequest(any(LoanRequestDTO.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        when(this.loanUseCase.createLoan(any(Loan.class))).thenReturn(Mono.just(createdLoan));

        doReturn(this.loan).when(this.loanApiMapper).toDomain(this.loanRequestDTO);
        doReturn(new LoanResponseDTO(
            createdLoan.getId(),
            createdLoan.getIdentificationNumber(),
            createdLoan.getAdjudicationDate(),
            createdLoan.getDeadline(),
            this.loanTypeDTO,
            createdLoan.getAmount(),
            createdLoan.getStatus().name()
        )).when(this.loanApiMapper).toResponse(any(Loan.class));

        this.webTestClient.post()
            .uri("/api/v1/requests")
            .bodyValue(this.loanRequestDTO)
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.status").isEqualTo("PENDING_OF_REVISION");
    }

    // --- PATCH /api/v1/requests/{id} ---
    @Test
    void shouldUpdateLoanStatus() {
        Loan updatedLoan = this.loan.toBuilder().status(Loan.LoanStatus.APPROVED).build();

        LoanResponseDTO updatedResponse = new LoanResponseDTO(
            updatedLoan.getId(),
            updatedLoan.getIdentificationNumber(),
            updatedLoan.getAdjudicationDate(),
            updatedLoan.getDeadline(),
            this.loanTypeDTO,
            updatedLoan.getAmount(),
            updatedLoan.getStatus().name()
        );

        when(this.loanUseCase.updateLoanStatus(any(UUID.class), anyString())).thenReturn(Mono.just(updatedLoan));
        /*when(this.loanApiMapper.toResponse(any(Loan.class))).thenReturn(updatedResponse);*/
        when(this.loanApiMapper.toResponse(any(Loan.class)))
            .thenAnswer(invocation -> {
                Loan input = invocation.getArgument(0);
                System.out.println("Mapper recibió: " + input.getStatus());
                return new LoanResponseDTO(
                    input.getId(),
                    input.getIdentificationNumber(),
                    input.getAdjudicationDate(),
                    input.getDeadline(),
                    this.loanTypeDTO,
                    input.getAmount(),
                    input.getStatus().name()
                );
            });
        /*when(this.loanApiMapper.toResponse(argThat(loan -> loan.getId().equals(updatedLoan.getId()))))
            .thenReturn(updatedResponse);*/

        this.webTestClient.patch()
            .uri("/api/v1/requests/{id}", this.loan.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(Map.of("status", "APPROVED"))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.status").isEqualTo("APPROVED");
    }
}
