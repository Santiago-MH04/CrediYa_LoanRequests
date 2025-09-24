package co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanType {
    /*MORTGAGE(1L, "Mortgage", new BigDecimal("0.13"), new BigInteger("12000000"), 120),
    HOME_RENOVATION(2L, "Home renovation", new BigDecimal("0.10"), new BigInteger("10000000"), 24),
    AUTOMOTIVE(3L, "Automotive loan", new BigDecimal("0.08"), new BigInteger("8000000"), 60),
    STUDENT(4L, "Educative loan", new BigDecimal("0.05"), new BigInteger("100000"), 48),
    MEDICAL(5L, "Medical loan", new BigDecimal("0.02"), new BigInteger("1000000"), 24),
    STARTUP(6L, "Start-up partnership loan", new BigDecimal("0.12"), new BigInteger("7500000"), 120),
    AGRICULTURE(7L, "Agriculture expansion loan", new BigDecimal("0.09"), new BigInteger("4500000"), 60),
    TRIP(8L, "Trip loan", new BigDecimal("0.05"), new BigInteger("3000000"), 12),
    PAYROLL(9L, "Payroll loan", new BigDecimal("0.15"), new BigInteger("15000000"), 6);*/

    private Long id;
    private String name;
    private BigDecimal interestRate;
    private BigInteger minimumBaseSalary;
    private int maximumDeadline;
}
