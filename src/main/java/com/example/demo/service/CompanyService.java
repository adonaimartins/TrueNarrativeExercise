package com.example.demo.service;

import com.example.demo.model.Company;
import com.example.demo.model.Officer;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.OfficerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyService
{
    private final CompanyRepository companyRepository;
    private final OfficerRepository officerRepository;

    public List<Company> getCompanyByNumberOrName(String companyQuery, String queryBy, boolean onlyActive)
    {
        Optional<List<Company>> companyOptional = companyRepository.findByQuery(companyQuery);
        List<Company> companyList;
        String companyNumber = companyQuery;

        if (companyOptional.isPresent()) {
            companyList = companyOptional.get();
            if(companyList.isEmpty() || !companyIsActive(companyList.get(0), onlyActive)){
                return new ArrayList<>();
            }

            //if we are querying by Name; we need to filter the result as the API search returns anything that contains the company name
            //then we find the company number in the results, so it can be used later to query officers by company number
            if(queryBy.equals("NAME")){
                companyList = filterCompanyByCompanyTitle(companyQuery, companyList);
                companyNumber = companyList.get(0).getCompanyNumber();
            }else{
                companyList = filterCompanyByCompanyNumber(companyQuery, companyList);
            }
        }else {
            return new ArrayList<>();
        }

        Optional<List<Officer>> officerOptional = officerRepository.findByCompanyNumber(companyNumber);

        //add officers to company if found
        if(officerOptional.isPresent()){
            List<Officer> officer = filterOfficersByResigned(officerOptional.get());
            companyList.get(0).setOfficers(officer);
        }

        return companyList;
    }

    private List<Company> filterCompanyByCompanyNumber(String companyQuery, List<Company> companyList) {
        return companyList
                .stream()
                .filter(
                        companyItem -> companyItem.getCompanyNumber().equalsIgnoreCase(companyQuery)
                )
                .toList();
    }

    private List<Company> filterCompanyByCompanyTitle(String companyQuery, List<Company> companyList) {
        return companyList
                .stream()
                .filter(
                        companyItem -> companyItem.getTitle().equalsIgnoreCase(companyQuery)
                )
                .toList();
    }

    private List<Officer> filterOfficersByResigned(List<Officer> officersList) {
        return officersList
                .stream()
                .filter(officerItem -> !officerItem.hasResigned())
                .toList();
    }

    private boolean companyIsActive(Company company, boolean onlyActive){
        return !(onlyActive && !company.getCompanyStatus().equalsIgnoreCase("active"));
    }
}
