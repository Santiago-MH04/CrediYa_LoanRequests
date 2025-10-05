package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.validations.exceptions.gateway;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GatewayCauses {
    REMOTE_SERVICE_UNAVAILABLE_ERROR("there is an internal error from the user’s datasource");

    private final String message;
}
