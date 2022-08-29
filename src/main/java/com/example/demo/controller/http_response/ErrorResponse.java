package com.example.demo.controller.http_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ErrorResponse implements Response
{
    @JsonProperty("total_results")
    private String errorMessage;
}
