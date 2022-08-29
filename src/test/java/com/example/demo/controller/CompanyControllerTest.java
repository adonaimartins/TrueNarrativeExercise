package com.example.demo.controller;

import com.example.demo.config.ApplicationProperties;
import com.example.demo.controller.body_objects.RequestBodyByNumberAndName;
import com.example.demo.controller.http_response.ErrorResponse;
import com.example.demo.controller.http_response.HttpResponse;
import com.example.demo.model.Company;
import com.example.demo.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @Mock
    private ApplicationProperties applicationProperties;

    @InjectMocks
    private CompanyController companyController;

    @Test
    void whenGetCompanyByNumberAndNameIsCalledWithCorrectApiKeyThenReturnStatusOk() {
        String apiKeyInHeader = "123456";
        RequestBodyByNumberAndName bodyRequest =
                RequestBodyByNumberAndName
                        .builder()
                        .companyNumber("123")
                        .companyName("ABC")
                        .build();

        boolean onlyActive = true;

        List<Company> companyList = new ArrayList<>();

        HttpResponse bodyResponse = HttpResponse.builder().items(companyList).totalResults(companyList.size()).build();

        when(companyService.getCompanyByNumberOrName("123", "NUMBER", true)).thenReturn(companyList);
        when(applicationProperties.getXApiKey()).thenReturn("123456");

        ResponseEntity<HttpResponse> response =
                (ResponseEntity<HttpResponse>) companyController.getCompanyByNumberAndName(
                        apiKeyInHeader,
                        bodyRequest,
                        onlyActive);

        verify(companyService, times(1)).getCompanyByNumberOrName("123", "NUMBER", true);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(bodyResponse);
    }

    @Test
    void whenGetCompanyByNumberAndNameIsCalledWithWrongApiKeyThenReturnStatusBadRequest() {
        String apiKeyInHeader = "111111";
        RequestBodyByNumberAndName bodyRequest =
                RequestBodyByNumberAndName
                        .builder()
                        .companyNumber("123")
                        .companyName("ABC")
                        .build();

        boolean onlyActive = true;

        when(applicationProperties.getXApiKey()).thenReturn("123456");

        ResponseEntity<ErrorResponse> response =
                (ResponseEntity<ErrorResponse>) companyController.getCompanyByNumberAndName(
                        apiKeyInHeader,
                        bodyRequest,
                        onlyActive);

        verify(companyService, times(0)).getCompanyByNumberOrName("123", "NUMBER", true);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getErrorMessage()).isEqualTo("Wrong API KEY");
    }
}