package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity
//@Table(name = "address", schema = "public")
public class Address
{
    private String locality;
    private String premises;
    @JsonProperty("address_line_1")
    private String addressLine1;
    private String country;
}