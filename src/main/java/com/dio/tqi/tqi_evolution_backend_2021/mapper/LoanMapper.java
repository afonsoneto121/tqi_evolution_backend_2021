package com.dio.tqi.tqi_evolution_backend_2021.mapper;

import com.dio.tqi.tqi_evolution_backend_2021.dto.request.LoanDTORequest;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.LoanDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.LoanDetailsDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.model.LoanModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanMapper {
    private final ObjectMapper objectMapper;

    public LoanModel dtoRequestToModel(LoanDTORequest loanDTORequest) {
        return objectMapper.convertValue(loanDTORequest,LoanModel.class);
    }
    public LoanDTOResponse modelToDtoResponse(LoanModel loanModel) {
        return objectMapper.convertValue(loanModel,LoanDTOResponse.class);
    }

    public LoanDetailsDTOResponse modelToDetailsDtoResponse( LoanModel loanModel) {
        LoanDetailsDTOResponse response = objectMapper.convertValue(loanModel, LoanDetailsDTOResponse.class);
        response.setEmail(loanModel.getUser().getEmail());
        response.setIncome(loanModel.getUser().getIncome());
        return response;
    }
}
