package com.dio.tqi.tqi_evolution_backend_2021.service;

import com.dio.tqi.tqi_evolution_backend_2021.exception.NotAuthorizedException;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.model.LoanModel;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.dio.tqi.tqi_evolution_backend_2021.repository.LoanRepository;
import com.dio.tqi.tqi_evolution_backend_2021.util.SecurityUtil;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserService userService;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private LoanService service;

    private LoanModel loan;
    private UserModel userModel;
    private HttpServletRequest request;
    @BeforeEach
    public void setup () {
        userModel = UserModel.builder()
                .id("1")
                .name("Test Name")
                .address("Test Address")
                .cpf("123456789")
                .email("test@test.com")
                .password("1234")
                .rg("987654321")
                .income(500.0F)
                .build();

        loan = LoanModel.builder()
                .id("1")
                .value(500F)
                .dateFirstInstallment(LocalDate.now())
                .numberInstallment(20)
                .build();
        request = new MockHttpServletRequest();
    }

    @Test
    public void whenValidLoanThenLoanShouldBeSaved() throws NotAuthorizedException, NotFound {
        Mockito.when(userService.findById(userModel.getId(), request)).thenReturn(userModel);
        Mockito.when(loanRepository.save(loan)).thenReturn(loan);

        LoanModel save = service.save(userModel.getId(), loan, request);

        MatcherAssert.assertThat(save, Matchers.equalTo(loan));
    }

    @Test
    public void whenInvalidIdUserThenAnExceptionShouldBeThrown() throws NotAuthorizedException, NotFound {
        Mockito.when(userService.findById(userModel.getId(), request)).thenThrow(new NotFound(""));

        Assertions.assertThrows(NotFound.class, () -> service.save(userModel.getId(), loan, request));
    }

    @Test
    public void whenIdUserNotAuthorizedThenAnExceptionShouldBeThrown() throws NotAuthorizedException, NotFound {
        Mockito.when(userService.findById(userModel.getId(), request)).thenThrow(new NotAuthorizedException(""));

        Assertions.assertThrows(NotAuthorizedException.class, () -> service.save(userModel.getId(), loan, request));
    }
    @Test
    public void whenValidRequestAllLoansByUserThenShouldBeReturned() throws NotAuthorizedException, NotFound {
        Pageable pageable = PageRequest.of(0,2);
        Mockito.when(userService.findById(userModel.getId(), request)).thenReturn(userModel);
        Mockito.when(loanRepository.findByUser(userModel,pageable))
                .thenReturn(new PageImpl(Arrays.asList(loan,loan,loan,loan), pageable, 4));

        Page<LoanModel> pages = service.getAllLoansByUser(userModel.getId(), pageable, request);

        MatcherAssert.assertThat(pages.getTotalElements(), Matchers.equalTo(4L));
        MatcherAssert.assertThat(pages.getTotalPages(), Matchers.equalTo(2));
        MatcherAssert.assertThat(pages.getSize(),Matchers.equalTo(2));

    }
    /*
    When id valid is passed, then a loan should be returned
     */
    @Test
    public void validId() throws NotAuthorizedException, NotFound {
        loan.setUser(userModel);
        Mockito.when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
        Mockito.doNothing().when(securityUtil).authorizedUser(request, userModel.getId());

        LoanModel byId = service.findById(loan.getId(), request);

        MatcherAssert.assertThat(byId, Matchers.equalTo(loan));

    }
    /*
    When invalid id is passed, then an exception should be thrown
     */
    @Test
    public void invalidId() throws NotAuthorizedException, NotFound {
        Mockito.when(loanRepository.findById(loan.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFound.class, () -> service.findById(loan.getId(), request));

    }

}
