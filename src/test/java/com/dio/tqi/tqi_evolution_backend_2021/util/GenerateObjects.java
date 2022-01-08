package com.dio.tqi.tqi_evolution_backend_2021.util;

import com.dio.tqi.tqi_evolution_backend_2021.dto.request.LoanDTORequest;
import com.dio.tqi.tqi_evolution_backend_2021.dto.request.UserDTORequest;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.LoanDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.LoanDetailsDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.UserDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.model.LoanModel;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import org.assertj.core.util.Arrays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;

public class GenerateObjects {
    public static UserModel getUserModel() {
        return UserModel.builder()
                .id("1")
                .name("Test Name")
                .address("Test Address")
                .cpf("123456789")
                .email("test@test.com")
                .password("1234")
                .rg("987654321")
                .income(500.0F)
                .build();
    }
    public static UserDTOResponse getUserDTOResponse() {
        return UserDTOResponse.builder()
                .id("1")
                .name("Test Name")
                .address("Test Address")
                .cpf("123456789")
                .email("test@test.com")
                .rg("987654321")
                .income(500.0F)
                .build();
    }
    public static UserDTORequest getUserDTORequest() {
        return UserDTORequest.builder()
                .name("Test Name")
                .address("Test Address")
                .cpf("123456789")
                .email("test@test.com")
                .password("1234")
                .rg("987654321")
                .income(Float.valueOf(500))
                .build();
    }

    public static LoanModel getLoanModel() {
        return LoanModel.builder()
                .id("1")
                .value(500F)
                .dateFirstInstallment(LocalDate.now())
                .numberInstallment(20)
                .build();
    }

    public static LoanDTORequest getLoanDTORequest(){
        return LoanDTORequest.builder()
                .value(500F)
                .dateFirstInstallment(LocalDate.now())
                .numberInstallment(20)
                .build();
    }

    public static LoanDTOResponse getLoanDTOResponse(){
        return LoanDTOResponse.builder()
                .id("1")
                .value(500F)
                .dateFirstInstallment(LocalDate.now())
                .numberInstallment(20)
                .build();
    }

    public static LoanDetailsDTOResponse getLoanDetailsDTOResponse(){
        LoanDetailsDTOResponse build = LoanDetailsDTOResponse.builder()
                .id("1")
                .value(500f)
                .dateFirstInstallment(LocalDate.now())
                .numberInstallment(20)
                .email("test@test.com")
                .income(500.0f)
                .build();
        return build;
    }

}
