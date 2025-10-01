package co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("loans")
public class LoanEntity {
    @Id
    private UUID id;

    @Column("name")
    private String identificationNumber;    //Just the identification number, and not the whole user, in order to separate responsibilities

    @Column("adjudication_date")
    private LocalDate adjudicationDate;

    @Column("deadline")
    private int deadline;   //The deadline will be measured in months

    @Column("loan_type_id")
    private Long loanTypeId;

    @Column("amount")
    private BigDecimal amount;

    @Column("status")
    private LoanStatus status;

    public enum LoanStatus {
        PENDING_OF_REVISION,
        APPROVED,
        REJECTED;
    }
}
