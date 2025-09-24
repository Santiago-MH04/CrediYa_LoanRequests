package co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.utils.LoanStatus;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan {
    private UUID loanId;
    private String identificationNumber;
    private LocalDate adjudicationDate;
    private int deadline; //The deadline will be measured in months
    private LoanType loanType;
    private BigDecimal amount;
    private LoanStatus status;
}
