package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.businessLogic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessLogicCauses {
    USER_NOT_FOUND_ERROR("the user you’re requiring the loan to, is not registered in our system"),
    INVALID_LOAN_TYPE_ERROR("we cannot lend you money on the loan type you’re applying for"),
    INSUFFICIENT_BASE_SALARY_ERROR("your base salary is not enough for us to meet your payment duties"),
    DEADLINE_OUT_OF_RANGE_ERROR("the deadline you propose to us exceeds the requirements for this kind of loan");

    private final String message;
}
