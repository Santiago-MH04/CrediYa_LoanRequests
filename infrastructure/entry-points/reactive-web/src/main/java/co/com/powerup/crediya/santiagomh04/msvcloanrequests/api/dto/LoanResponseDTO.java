package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LoanResponseDTO(
    UUID id,
    String identificationNumber,    //Just the identification number, and not the whole user, in order to separate responsibilities
    LocalDate adjudicationDate,
    int deadline,   //The deadline will be measured in months
    LoanType loanType,
    BigDecimal amount,
    String status
){
}
