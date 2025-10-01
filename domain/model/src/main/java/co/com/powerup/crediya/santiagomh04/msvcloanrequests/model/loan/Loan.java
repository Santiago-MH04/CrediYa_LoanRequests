package co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan;

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
    private UUID id;
    private String identificationNumber;    //Just the identification number, and not the whole user, in order to separate responsibilities
    private LocalDate adjudicationDate;
    private int deadline;   //The deadline will be measured in months
    private LoanType loanType;
    private BigDecimal amount;
    private LoanStatus status;
        /*private String status;*/

    public enum LoanStatus {
        PENDING_OF_REVISION,
        APPROVED,
        REJECTED;
    }
}
