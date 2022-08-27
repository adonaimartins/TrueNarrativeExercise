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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

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

    @GetMapping(params = {"onlyActive"})
    public ResponseEntity<HttpResponse> getCompanyByNumberAndName(
            @RequestHeader(HttpHeaders.X_API_KEY) @NotNull String apiKeyInHeader,
            @RequestBody /*@ValidateCompanyByIdAndNameJson*/ RequestBodyByNumberAndName body,
            @RequestParam("onlyActive") boolean onlyActive
    ) {

        if(!apiKeyInHeader.equals(applicationProperties.getXApiKey())){
            return new ResponseEntity<>(HttpResponse.builder().build(), BAD_REQUEST);//need to change later
        }

        List<Company> companyList;
        try {

            String queryBy = calculateCompanyQueryBy(body);
            companyList = companyService.getCompanyByNumberOrName(
                    queryBy.equals("NUMBER") ? body.getCompanyNumber(): body.getCompanyName(),
                    queryBy,
                    onlyActive
            );

        } catch (Exception e) {
            return new ResponseEntity<>(HttpResponse.builder().build(), BAD_REQUEST);//need to change later
        }
        return new ResponseEntity<>(HttpResponse.builder().items(companyList).totalResults(companyList.size()).build(), OK);
    }


    private String calculateCompanyQueryBy(RequestBodyByNumberAndName body){
        if (StringUtils.hasText(body.getCompanyName()) && StringUtils.hasText(body.getCompanyNumber())){
            return "NUMBER";
        }else if (!StringUtils.hasText(body.getCompanyName()) && StringUtils.hasText(body.getCompanyNumber())){
            return "NUMBER";

        }else if (StringUtils.hasText(body.getCompanyName()) && !StringUtils.hasText(body.getCompanyNumber())){
            return "NAME";
        }else {
            return "NUMBER";
        }
    }
}
