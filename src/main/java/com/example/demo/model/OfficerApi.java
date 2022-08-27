package com.example.demo.model;

import com.example.demo.service.WebClientClass;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfficerApi implements WebClientClass
{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Officer> items;
}
