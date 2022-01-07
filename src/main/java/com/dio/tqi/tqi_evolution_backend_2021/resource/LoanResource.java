package com.dio.tqi.tqi_evolution_backend_2021.resource;

import com.dio.tqi.tqi_evolution_backend_2021.dto.request.LoanDTORequest;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.LoanDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.LoanDetailsDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotAuthorizedException;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.mapper.LoanMapper;
import com.dio.tqi.tqi_evolution_backend_2021.model.LoanModel;
import com.dio.tqi.tqi_evolution_backend_2021.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/loans")
@RequiredArgsConstructor
public class LoanResource {

    private final LoanService loanService;
    private final LoanMapper mapper;
    @PostMapping("/{idUser}")
    public ResponseEntity<LoanDTOResponse> save(@PathVariable String idUser,
                                                @RequestBody @Valid LoanDTORequest loanDTORequest,
                                                HttpServletRequest request) throws NotAuthorizedException, NotFound {
        LoanModel loanModel = mapper.dtoRequestToModel(loanDTORequest);
        LoanModel save = loanService.save(idUser,loanModel,request);
        LoanDTOResponse loanDTOResponse = mapper.modelToDtoResponse(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanDTOResponse);
    }

    @GetMapping("/{idUser}/loans")
    public ResponseEntity<Page<LoanDTOResponse>> loansByUser(@PathVariable String idUser,
                                                             Pageable pageable,
                                                             HttpServletRequest request) throws NotAuthorizedException, NotFound {
        Page<LoanModel> allLoansByUser = loanService.getAllLoansByUser(idUser,pageable, request);
        Page<LoanDTOResponse> map = allLoansByUser.map(mapper::modelToDtoResponse);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/details/{idLoan}")
    public ResponseEntity<LoanDetailsDTOResponse> findById(@PathVariable String idLoan,
                                                           HttpServletRequest request) throws NotFound, NotAuthorizedException {
        LoanModel byId = loanService.findById(idLoan, request);
        LoanDetailsDTOResponse loanDetailsDTOResponse = mapper.modelToDetailsDtoResponse(byId);
        return ResponseEntity.ok(loanDetailsDTOResponse);
    }
}
