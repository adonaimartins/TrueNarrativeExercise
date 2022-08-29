package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.Company;
import com.example.demo.model.Officer;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.OfficerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest
{
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private OfficerRepository officerRepository;
    @InjectMocks
    private CompanyService companyService;

    private Officer officer1;
    private Officer officer2;
    private Officer officer3Resigned;
    private Company company; //company is active. we can set it to inactive later

    private void initialiseObjects(){
        officer1 = Officer
                .builder()
                .name("BOXALL, Sarah Victoria")
                .address(new Address())
                .appointedOn("2008-02-11")
                .officerRole("secretary")
                .build();

        officer2 = Officer
                .builder()
                .name("BRAY, Simon Anton")
                .address(new Address())
                .appointedOn("2008-02-11")
                .officerRole("director")
                .build();

        officer3Resigned = Officer
                .builder()
                .name("BRAY, Simon Anton")
                .address(new Address())
                .appointedOn("2008-02-11")
                .officerRole("director")
                .resignedOn("2008-02-12") //resigned
                .build();

        company = Company
                .builder()
                .companyNumber("06500244")
                .title("BBC LIMITED")
                .address(new Address())
                .companyType("")
                .companyStatus("active") //active/innactive
                .dateOfCreation("")
                .build();
    }

    @Test
    void whenQueryingByNumberAndOnlyActiveTrueCompaniesWithResignedOfficerThenReturnsTheExpectedCompanySkippingResignedOfficer() {
        initialiseObjects();

        when(companyRepository.findByQuery("06500244")).thenReturn(Optional.of(List.of(company)));
        when(officerRepository.findByCompanyNumber("06500244")).thenReturn(Optional.of(List.of(officer1, officer2, officer3Resigned)));

        List<Company> actual = companyService.getCompanyByNumberOrName("06500244", "NUMBER", true);


        verify(companyRepository, times(1)).findByQuery("06500244");
        verify(officerRepository, times(1)).findByCompanyNumber("06500244");

        //verify it does not contain "Inactive Companies"
        assertThat(actual.stream().filter(companyItem -> !companyItem.getCompanyStatus().equals("active")).toList().size()).isZero();

        //verify officers that have not resigned
        assertEquals(
                0,
                actual
                        .stream()
                        .map(Company::getOfficers)
                        .filter(officerItem -> officerItem.get(0).hasResigned())
                        .toList()
                        .size()
        );

        Company expected = company;

        assertThat(actual.get(0)).isEqualTo(expected);
        assertThat(actual.get(0).getOfficers().size()).isEqualTo(2);
    }

    @Test
    void whenQueryingByNumberAndOnlyActiveTrueCompaniesWithActiveCompanyThenReturnsTheExpectedCompany() {
        initialiseObjects();

        when(companyRepository.findByQuery("06500244")).thenReturn(Optional.of(List.of(company)));
        when(officerRepository.findByCompanyNumber("06500244")).thenReturn(Optional.of(List.of(officer1, officer2)));

        List<Company> actual = companyService.getCompanyByNumberOrName("06500244", "NUMBER", true);


        verify(companyRepository, times(1)).findByQuery("06500244");
        verify(officerRepository, times(1)).findByCompanyNumber("06500244");

        //verify it does not contain "Inactive Companies"
        assertThat(actual.stream().filter(companyItem -> !companyItem.getCompanyStatus().equals("active")).toList().size()).isZero();

        //verify officers that have not resigned
        assertEquals(
                0,
                actual
                        .stream()
                        .map(Company::getOfficers)
                        .filter(officerItem -> officerItem.get(0).hasResigned())
                        .toList()
                        .size()
        );

        Company expected = company;

        assertThat(actual.get(0)).isEqualTo(expected);
    }

    @Test
    void whenQueryingByNameAndOnlyActiveCompaniesTrueWithActiveCompanyThenReturnsTheExpectedCompany() {
        initialiseObjects();

        when(companyRepository.findByQuery("BBC LIMITED")).thenReturn(Optional.of(List.of(company)));
        when(officerRepository.findByCompanyNumber("06500244")).thenReturn(Optional.of(List.of(officer1, officer2)));

        List<Company> actual = companyService.getCompanyByNumberOrName("BBC LIMITED", "NAME", true);


        verify(companyRepository, times(1)).findByQuery("BBC LIMITED");
        verify(officerRepository, times(1)).findByCompanyNumber("06500244");

        //verify it does not contain "Inactive Companies"
        assertThat(actual.stream().filter(companyItem -> !companyItem.getCompanyStatus().equals("active")).toList().size()).isZero();

        //verify officers that have not resigned
        assertEquals(
                0,
                actual
                        .stream()
                        .map(Company::getOfficers)
                        .filter(officerItem -> officerItem.get(0).hasResigned())
                        .toList()
                        .size()
        );

        Company expected = company;

        assertThat(actual.get(0)).isEqualTo(expected);
    }

    @Test
    void whenQueryingAndOnlyActiveCompaniesTrueWithInactiveCompanyThenReturnsEmptyArray() {

        initialiseObjects();
        company.setCompanyStatus("liquidation");

        when(companyRepository.findByQuery("06500244")).thenReturn(Optional.of(List.of(company)));

        List<Company> actual = companyService.getCompanyByNumberOrName("06500244", "NUMBER", true);

        verify(companyRepository, times(1)).findByQuery("06500244");
        verify(officerRepository, times(0)).findByCompanyNumber("06500244");

        assertThat(actual.size()).isZero();
    }

    @Test
    void whenQueryingByNumberAndOnlyActiveFalseCompaniesWithActiveCompanyThenReturnsTheExpectedCompany() {
        initialiseObjects();
        company.setCompanyStatus("liquidation");

        when(companyRepository.findByQuery("06500244")).thenReturn(Optional.of(List.of(company)));
        when(officerRepository.findByCompanyNumber("06500244")).thenReturn(Optional.of(List.of(officer1, officer2)));

        List<Company> actual = companyService.getCompanyByNumberOrName("06500244", "NUMBER", false);


        verify(companyRepository, times(1)).findByQuery("06500244");
        verify(officerRepository, times(1)).findByCompanyNumber("06500244");

        //verify it does not contain "Inactive Companies"
        assertThat(actual.stream().filter(companyItem -> !companyItem.getCompanyStatus().equals("active")).toList().size()).isEqualTo(1);

        //verify officers that have not resigned
        assertEquals(
                0,
                actual
                        .stream()
                        .map(Company::getOfficers)
                        .filter(officerItem -> officerItem.get(0).hasResigned())
                        .toList()
                        .size()
        );

        Company expected = company;

        assertThat(actual.get(0)).isEqualTo(expected);
    }

    @Test
    void whenQueryingByNameAndOnlyActiveCompaniesFalseWithActiveCompanyThenReturnsTheExpectedCompany() {
        initialiseObjects();
        company.setCompanyStatus("liquidation");

        when(companyRepository.findByQuery("BBC LIMITED")).thenReturn(Optional.of(List.of(company)));
        when(officerRepository.findByCompanyNumber("06500244")).thenReturn(Optional.of(List.of(officer1, officer2)));

        List<Company> actual = companyService.getCompanyByNumberOrName("BBC LIMITED", "NAME", false);


        verify(companyRepository, times(1)).findByQuery("BBC LIMITED");
        verify(officerRepository, times(1)).findByCompanyNumber("06500244");

        //verify it does not contain "Inactive Companies"
        assertThat(actual.stream().filter(companyItem -> !companyItem.getCompanyStatus().equals("active")).toList().size()).isEqualTo(1);

        //verify officers that have not resigned
        assertEquals(
                0,
                actual
                        .stream()
                        .map(Company::getOfficers)
                        .filter(officerItem -> officerItem.get(0).hasResigned())
                        .toList()
                        .size()
        );

        Company expected = company;

        assertThat(actual.get(0)).isEqualTo(expected);
    }
}




/*
        CompanyService companyServiceSpy = Mockito.spy(companyService);
        companyServiceSpy.getCompanyByNumberOrName();

        //verify the spy
        verify(ehrExtractMessageHandlerSpy, times(0)).sendContinueRequest(
                any(RCMRIN030000UK06Message.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(Instant.class)
        );
*/