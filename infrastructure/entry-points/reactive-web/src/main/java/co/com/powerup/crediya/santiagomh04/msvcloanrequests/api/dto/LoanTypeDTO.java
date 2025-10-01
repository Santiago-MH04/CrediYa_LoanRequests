package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public record LoanTypeDTO(
    Long id,
    String name,
    BigDecimal interestRate,
    BigInteger minimumBaseSalary,
    int maximumDeadline
){
}
