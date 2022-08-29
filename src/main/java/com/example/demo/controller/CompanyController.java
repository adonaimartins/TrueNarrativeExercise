package com.example.demo.controller;

import com.example.demo.config.ApplicationProperties;
import com.example.demo.controller.body_objects.RequestBodyByNumberAndName;
import com.example.demo.controller.headers.HttpHeaders;
import com.example.demo.controller.http_response.ErrorResponse;
import com.example.demo.controller.http_response.HttpResponse;
import com.example.demo.controller.http_response.Response;
import com.example.demo.model.Company;
import com.example.demo.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "company")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyController
{
    private final ApplicationProperties applicationProperties;
    private final CompanyService companyService;
    private static final String NUMBER = "NUMBER";


    @GetMapping(params = {"onlyActive"})
    public ResponseEntity<? extends Response> getCompanyByNumberAndName(
            @RequestHeader(HttpHeaders.X_API_KEY) @NotNull String apiKeyInHeader,
            @RequestBody /*@ValidateCompanyByIdAndNameJson*/ RequestBodyByNumberAndName body,
            @RequestParam("onlyActive") boolean onlyActive
    ) {

        if(!apiKeyInHeader.equals(applicationProperties.getXApiKey())){
            return new ResponseEntity<>(ErrorResponse.builder().errorMessage("Wrong API KEY").build(), BAD_REQUEST);//need to change later
        }

        String queryBy = calculateCompanyQueryBy(body);
        List<Company> companyList = companyService.getCompanyByNumberOrName(
                queryBy.equals(NUMBER) ? body.getCompanyNumber(): body.getCompanyName(),
                queryBy,
                onlyActive
        );

        return new ResponseEntity<>(HttpResponse.builder().items(companyList).totalResults(companyList.size()).build(), OK);
    }


    private String calculateCompanyQueryBy(RequestBodyByNumberAndName body){
        if (StringUtils.hasText(body.getCompanyName()) && StringUtils.hasText(body.getCompanyNumber())){
            return NUMBER;
        }else if (!StringUtils.hasText(body.getCompanyName()) && StringUtils.hasText(body.getCompanyNumber())){
            return NUMBER;

        }else if (StringUtils.hasText(body.getCompanyName()) && !StringUtils.hasText(body.getCompanyNumber())){
            return "NAME";
        }else {
            return NUMBER;
        }
    }
}
