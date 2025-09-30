package co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("loan_types")
public class LoanTypeEntity {
    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("interest_rate")
    private BigDecimal interestRate;

    @Column("minimum_base_salary")
    private BigInteger minimumBaseSalary;

    @Column("maximum_deadline")
    private int maximumDeadline;
}
