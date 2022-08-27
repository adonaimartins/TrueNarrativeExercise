package com.example.demo.controller.http_response;

import com.example.demo.model.Company;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HttpResponse
{
    @JsonProperty("total_results")
    private int totalResults;
    private List<Company> items;
}