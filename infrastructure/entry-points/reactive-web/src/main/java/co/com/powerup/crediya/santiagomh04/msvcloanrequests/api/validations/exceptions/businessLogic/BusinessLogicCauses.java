package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.businessLogic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessLogicCauses {
    USER_NOT_FOUND_ERROR("the user you’re requiring the loan to, is not registered in our system");

    private final String message;
}
