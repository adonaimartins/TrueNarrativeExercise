package com.example.demo.model;

import com.example.demo.service.WebClientClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Jacksonized
public class Officer
{

    private String name;
    @JsonProperty("officer_role")
    private String officerRole;
    @JsonProperty("appointed_on")
    private String appointedOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Address address;
    @JsonIgnore
    @JsonProperty("resigned_on")
    private String resignedOn;

    public String getName() {
        return name;
    }

    public String getOfficerRole() {
        return officerRole;
    }

    public String getAppointedOn() {
        return appointedOn;
    }

    public Address getAddress() {
        return address;
    }

    public boolean hasResigned(){
        return resignedOn != null;
    }
}
