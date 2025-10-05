package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.requestBody;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestBodyCauses {
    EMPTY_FIELD_ERROR("these fields cannot be empty, nor null"),
    INVALID_LOAN_TYPE_ERROR("we cannot lend you money on the loan type you’re applying for"),
    INSUFFICIENT_BASE_SALARY_ERROR("your base salary is not enough for us to meet your payment duties"),
    DEADLINE_OUT_OF_RANGE_ERROR("the deadline you propose to us exceeds the requirements for this kind of loan");

    private final String message;
}
