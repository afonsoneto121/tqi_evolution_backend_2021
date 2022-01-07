package com.dio.tqi.tqi_evolution_backend_2021.service;

import com.dio.tqi.tqi_evolution_backend_2021.exception.NotAuthorizedException;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.model.LoanModel;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.dio.tqi.tqi_evolution_backend_2021.repository.LoanRepository;
import com.dio.tqi.tqi_evolution_backend_2021.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserService userService;
    private final SecurityUtil securityUtil;
    public LoanModel save(String id, LoanModel loanModel, HttpServletRequest request) throws NotAuthorizedException, NotFound {
        UserModel byId = userService.findById(id, request);
        loanModel.setUser(byId);
        return loanRepository.save(loanModel);
    }

    public Page<LoanModel> getAllLoansByUser(String idUser, Pageable pageable, HttpServletRequest request) throws NotAuthorizedException, NotFound {
        UserModel byId = userService.findById(idUser, request);
        //return byId.getLoan();
        return loanRepository.findByUser(byId, pageable);
    }

    public LoanModel findById(String id, HttpServletRequest request) throws NotFound, NotAuthorizedException {
        LoanModel loanModel = loanRepository.findById(id)
                .orElseThrow(() -> new NotFound("Resource not found"));
        securityUtil.authorizedUser(request, loanModel.getUser().getId());
        return loanModel;
    }
}
