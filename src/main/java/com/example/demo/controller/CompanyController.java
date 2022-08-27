package com.example.demo.controller;

import com.example.demo.config.ApplicationProperties;
import com.example.demo.controller.bodyObjects.RequestBodyByNumberAndName;
import com.example.demo.controller.headers.HttpHeaders;
import com.example.demo.controller.http_response.HttpResponse;
import com.example.demo.model.Company;
import com.example.demo.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "company")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyController
{
    private final ApplicationProperties applicationProperties;
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<HttpResponse> getCompanyByNumberAndName(
            @RequestHeader(HttpHeaders.X_API_KEY) @NotNull String apiKeyInHeader,
            @RequestBody /*@ValidateCompanyByIdAndNameJson*/ RequestBodyByNumberAndName body
    ) {

        if(!apiKeyInHeader.equals(applicationProperties.getXApiKey())){
            return new ResponseEntity<>(HttpResponse.builder().build(), BAD_REQUEST);//need to change later
        }

        List<Company> companyList = new ArrayList<>();
        try {
            companyList.add(companyService.getCompanyByNumberAndName(body.getCompanyNumber(), body.getCompanyName()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpResponse.builder().build(), BAD_REQUEST);//need to change later
        }
        return new ResponseEntity<>(HttpResponse.builder().items(companyList).totalResults(companyList.size()).build(), OK);
    }
}
