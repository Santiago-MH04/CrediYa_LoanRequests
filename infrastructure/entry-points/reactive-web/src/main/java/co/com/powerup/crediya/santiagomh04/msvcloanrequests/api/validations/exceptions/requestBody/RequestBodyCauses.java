package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.requestBody;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestBodyCauses {
    EMPTY_FIELD_ERROR("these fields cannot be empty, nor null"),
    LOANS_NOT_FOUND_ERROR("the");

    private final String message;
}
