package com.example.demo.service;

import com.example.demo.model.Company;
import com.example.demo.model.CompanyApi;
import com.example.demo.model.Officer;
import com.example.demo.model.OfficerApi;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.OfficerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyService
{
    private final CompanyRepository companyRepository;
    private final OfficerRepository officerRepository;

    public Company getCompanyByNumberAndName(String companyNumber, String companyName) throws Exception {
        Optional<List<Company>> companyOptional = companyRepository.findByNumber(companyNumber);
        Optional<List<Officer>> officerOptional = officerRepository.findByCompanyNumber(companyNumber);

        if(companyOptional.isPresent() && officerOptional.isPresent()){

            Company company = companyOptional.get().get(0);
            List<Officer> officer = officerOptional.get();

            company.setOfficers(officer);

            return company;
        }else {
            throw new Exception("error message");
        }
    }
}
