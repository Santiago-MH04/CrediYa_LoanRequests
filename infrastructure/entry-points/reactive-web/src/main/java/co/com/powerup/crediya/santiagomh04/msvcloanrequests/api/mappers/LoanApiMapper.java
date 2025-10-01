package co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.mappers;

import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.api.dto.LoanResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loan.Loan;
import co.com.powerup.crediya.santiagomh04.msvcloanrequests.model.loantype.LoanType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {LoanTypeApiMapper.class})
public interface LoanApiMapper {
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "status", ignore = true),
        @Mapping(target = "loanType", source = "loanTypeName")
    })
    Loan toDomain(LoanRequestDTO loanRequestDTO);

    LoanResponseDTO toResponse(Loan loan);

    // Auxiliary method: maps the name into a partial LoanType
    default LoanType mapLoanType(String loanTypeName) {
        return LoanType.builder()
            .name(loanTypeName)
            .build();
    }

}
