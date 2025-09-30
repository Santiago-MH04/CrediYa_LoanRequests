package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.mappers;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanTypeDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanTypeApiMapper {
    LoanTypeDTO toResponse(LoanType loanType);
}
