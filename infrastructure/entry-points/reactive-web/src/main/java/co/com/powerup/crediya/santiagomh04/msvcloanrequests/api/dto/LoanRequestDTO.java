package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanRequestDTO(
    String identificationNumber,
    LocalDate adjudicationDate,
    int deadline,
    String loanTypeName,
    BigDecimal amount/*,
    String status*/
){
}
