package com.example.demo.repository;

import com.example.demo.model.Officer;
import com.example.demo.model.OfficerApi;
import com.example.demo.service.WebClientClass;
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
public class OfficerRepository implements WebClientClass
{
    private final WebClient webClientBuilder;
    private final WebClientService webClientService;

    private static final String URI_QUERY_STRING = "Officers?CompanyNumber=";
    private static final String CONTENT_TYPE = "Content-type";

    public Optional<List<Officer>> findByCompanyNumber(String companyNumber)
    {
        var request = buildGetCompanyRequest("/Companies/v1/" + URI_QUERY_STRING + companyNumber);

        OfficerApi response = (OfficerApi) webClientService.send(request, OfficerApi.class);

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
