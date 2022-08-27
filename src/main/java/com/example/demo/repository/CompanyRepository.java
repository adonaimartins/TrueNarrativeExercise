package com.example.demo.repository;

import com.example.demo.model.Company;
import com.example.demo.model.CompanyApi;
import com.example.demo.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyRepository
{
    private final WebClient webClientBuilder;
    private final WebClientService webClientService;

    private static final String URI_QUERY_STRING = "Search?Query=";
    private static final String CONTENT_TYPE = "Content-type";


    public Optional<List<Company>> findByNumber(String companyNumber)
    {
        var request = buildGetCompanyRequest("/Companies/v1/" + URI_QUERY_STRING + companyNumber);

        CompanyApi response = (CompanyApi) webClientService.send(request, CompanyApi.class);

        if(response == null){
            return Optional.empty();
        }else {
            return Optional.of(response.getItems());
        }
    }

    private WebClient.RequestHeadersSpec<?> buildGetCompanyRequest(String url) {
        return webClientBuilder
                .method(HttpMethod.GET)
                .uri(url)
                .accept(APPLICATION_JSON)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    }
}