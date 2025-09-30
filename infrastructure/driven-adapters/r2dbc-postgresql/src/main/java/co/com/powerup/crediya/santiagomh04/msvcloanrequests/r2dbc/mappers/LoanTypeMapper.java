package co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.mappers;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.r2dbc.entities.LoanTypeEntity;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanTypeMapper {

    private final ObjectMapper objectMapper;

    public LoanType toDomain(LoanTypeEntity loanTypeEntity){
        return this.objectMapper.map(loanTypeEntity, LoanType.class);
    }

    public LoanTypeEntity toEntity(LoanType loanType){
        return this.objectMapper.map(loanType, LoanTypeEntity.class);
    }
}
