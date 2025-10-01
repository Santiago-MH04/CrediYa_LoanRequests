package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LoanResponseDTO(
    UUID id,
    String identificationNumber,    //Just the identification number, and not the whole user, in order to separate responsibilities
    LocalDate adjudicationDate,
    int deadline,   //The deadline will be measured in months
    LoanTypeDTO loanType,
    BigDecimal amount,
    String status
){
}
