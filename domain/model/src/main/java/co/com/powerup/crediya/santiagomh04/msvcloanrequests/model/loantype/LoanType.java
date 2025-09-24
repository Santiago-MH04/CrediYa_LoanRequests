package co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanType {
    /*MORTGAGE(1L, "Mortgage", new BigDecimal("0.13"), 120),
    HOME_RENOVATION(2L, "Home renovation", new BigDecimal("0.10"), 24),
    AUTOMOTIVE(3L, "Automotive loan", new BigDecimal("0.08"), 60),
    STUDENT(4L, "Educative loan", new BigDecimal("0.05"), 48),
    MEDICAL(5L, "Medical loan", new BigDecimal("0.02"), 24),
    STARTUP(6L, "Start-up partnership loan", new BigDecimal("0.12"), 120),
    AGRICULTURE(7L, "Agriculture expansion loan", new BigDecimal("0.09"), 60),
    TRIP(8L, "Trip loan", new BigDecimal("0.05"), 12),
    PAYROLL(9L, "Payroll loan", new BigDecimal("0.15"), 6);*/

    private Long id;
    private String name;
    private BigDecimal interestRate;
    private int maximumDeadline;
}
