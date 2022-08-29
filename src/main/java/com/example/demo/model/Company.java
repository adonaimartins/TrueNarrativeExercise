package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

//import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity
//@Table(name = "company", schema = "public")
public class Company
{
    @JsonProperty("company_number")
    //@Column(name = "company_number")
    private String companyNumber;
    @JsonProperty("company_type")
    //@Column(name = "company_type")
    private String companyType;
    //@Column(name = "title")
    private String title;
    @JsonProperty("company_status")
    //@Column(name = "company_status")
    private String companyStatus;
    @JsonProperty("date_of_creation")
    //@Column(name = "date_of_creation")
    private String dateOfCreation;
    //@Column(name = "address")
    private Address address;
    @JsonManagedReference
    //@OneToMany(mappedBy = "patientMigrationRequest", cascade = CascadeType.ALL)
    private List<Officer> officers;
}