package com.dio.tqi.tqi_evolution_backend_2021.resource;

import com.dio.tqi.tqi_evolution_backend_2021.dto.request.LoanDTORequest;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.LoanDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.LoanDetailsDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.mapper.LoanMapper;
import com.dio.tqi.tqi_evolution_backend_2021.model.LoanModel;
import com.dio.tqi.tqi_evolution_backend_2021.resource.advice.UserResourceAdvice;
import com.dio.tqi.tqi_evolution_backend_2021.service.LoanService;
import org.hamcrest.Matchers;
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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDate;
import java.util.Arrays;

import static com.dio.tqi.tqi_evolution_backend_2021.util.GenerateObjects.*;
import static com.dio.tqi.tqi_evolution_backend_2021.util.JsonUtil.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LoanResourceTest {
    private MockMvc mvc;
    @Mock
    private LoanService loanService;
    @Mock
    private LoanMapper mapper;
    @InjectMocks
    private LoanResource loanResource;

    private LoanModel loanModel;
    private LoanDTORequest dtoRequest;
    private LoanDTOResponse dtoResponse;
    private LoanDetailsDTOResponse detailsDTOResponse;
    private final String ID_USER = "1";
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(loanResource)
                .setControllerAdvice(new UserResourceAdvice())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
        loanModel = getLoanModel();
        dtoRequest = getLoanDTORequest();
        dtoResponse = getLoanDTOResponse();
        detailsDTOResponse = getLoanDetailsDTOResponse();
    }
    /*
    Given a valid loan
    When request save loan
    Then should be return status 201 CREATED
     */
    @Test
    public void saveValidLoan() throws Exception {
        Mockito.when(mapper.dtoRequestToModel(dtoRequest)).thenReturn(loanModel);
        Mockito.when(mapper.modelToDtoResponse(loanModel)).thenReturn(dtoResponse);
        mvc.perform(post("/api/v1/loans/"+ID_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dtoRequest))
                .with(request -> {
                    try {
                        Mockito.when(loanService.save(ID_USER,loanModel,request)).thenReturn(loanModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return request;
                }))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(dtoResponse.getId())))
                .andExpect(jsonPath("$.numberInstallment", Matchers.equalTo(dtoResponse.getNumberInstallment())));
    }

    /*
    Given an invalid loan with value 0
    When request save loan
    Then should be return status 400 BAD_REQUEST
     */
    @Test
    public void saveWithValueZero() throws Exception {
        LoanDTORequest badRequest = getLoanDTORequest();
        badRequest.setValue(0F);

        mvc.perform(post("/api/v1/loans/"+ID_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(badRequest)))
                .andExpect(status().isBadRequest());
    }

    /*
    Given an invalid loan without date
    When request save loan
    Then should be return status 400 BAD_REQUEST
     */
    @Test
    public void saveWithoutDate() throws Exception {
        LoanDTORequest badRequest = getLoanDTORequest();
        badRequest.setDateFirstInstallment(null);

        mvc.perform(post("/api/v1/loans/"+ID_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(badRequest)))
                .andExpect(status().isBadRequest());
    }

    /*
    Given an invalid loan with incorrect date first installment
    When request save loan
    Then should be return status 400 BAD_REQUEST
     */
    @Test
    public void saveWithInvalidDate() throws Exception {
        LoanDTORequest badRequest = getLoanDTORequest();
        badRequest.setDateFirstInstallment(
                LocalDate.now().plusDays(1).plusMonths(3)
        );

        mvc.perform(post("/api/v1/loans/"+ID_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(badRequest)))
                .andExpect(status().isBadRequest());
    }

    /*
    Given an invalid loan with incorrect number installment > 60
    When request save loan
    Then should be return status 400 BAD_REQUEST
     */
    @Test
    public void saveWithInvalidNumber() throws Exception {
        LoanDTORequest badRequest = getLoanDTORequest();
        badRequest.setNumberInstallment(61);

        mvc.perform(post("/api/v1/loans/"+ID_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(badRequest)))
                .andExpect(status().isBadRequest());
    }

    /*
    Given an invalid loan with incorrect number installment < 0
    When request save loan
    Then should be return status 400 BAD_REQUEST
     */
    @Test
    public void saveWithInvalidNumber2() throws Exception {
        LoanDTORequest badRequest = getLoanDTORequest();
        badRequest.setNumberInstallment(-1);

        mvc.perform(post("/api/v1/loans/"+ID_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(badRequest)))
                .andExpect(status().isBadRequest());
    }

    /*
    Given user valid
    When request get all loans
    Then should be return status 200 OK
     */
    @Test
    public void getLoansByUser() throws Exception {
        Pageable pageable = PageRequest.of(0,3);
        Mockito.when(mapper.modelToDtoResponse(loanModel)).thenReturn(dtoResponse);
        Page<LoanModel> page = new PageImpl(Arrays.asList(loanModel, loanModel, loanModel, loanModel,loanModel), pageable, 5);
        mvc.perform(get(String.format("/api/v1/loans/%s/loans?page=0&size=3",ID_USER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(request -> {
                        try {
                            Mockito.when(loanService.getAllLoansByUser(ID_USER,pageable,request)).thenReturn(page);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return request;
                    }))
                .andExpect(status().isOk());
    }
    /*
    Given valid id loan
    When request details of loan
    Then should be return details with status 200 OK
     */
    @Test
    public void getDetailsLoan() throws Exception {
        Mockito.when(mapper.modelToDetailsDtoResponse(loanModel)).thenReturn(detailsDTOResponse);
        mvc.perform(get(String.format("/api/v1/loans/details/%s",loanModel.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            try {
                                Mockito.when(loanService.findById(loanModel.getId(),request)).thenReturn(loanModel);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(detailsDTOResponse.getId())))
                .andExpect(jsonPath("$.email", Matchers.equalTo(detailsDTOResponse.getEmail())))
                .andExpect(jsonPath("$.income", Matchers.is(Double.parseDouble(String.valueOf(detailsDTOResponse.getIncome())))))
                .andExpect(jsonPath("$.value", Matchers.equalTo(Double.parseDouble(String.valueOf(detailsDTOResponse.getValue())))));
    }
}
