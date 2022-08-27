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

    public List<Company> getCompanyByNumberOrName(String companyQuery, String queryBy, boolean onlyActive) throws Exception
    {
        Optional<List<Company>> companyOptional = companyRepository.findByQuery(companyQuery);
        List<Company> companyList;
        String companyNumber = companyQuery;

        //if we are querying by Name; we need to filter the result, find the company number so it can be used later
        //otherwise, we just query by number
        if(companyOptional.isPresent()){
            companyList = companyOptional.get();
            if(companyList.size() == 0){
                return new ArrayList<>();
            }

            if(queryBy.equals("NAME")){
                companyList = companyList
                        .stream()
                        .filter(
                                companyItem -> companyItem.getTitle().equalsIgnoreCase(companyQuery)
                        )
                        .toList();

                companyNumber = companyList.get(0).getCompanyNumber();
            }
        }else {
            throw new Exception("company not found exception = ");
        }

        Optional<List<Officer>> officerOptional = officerRepository.findByCompanyNumber(companyNumber);

        if(officerOptional.isPresent()){

            List<Officer> officer = officerOptional
                    .get()
                    .stream()
                    .filter(officerItem -> !officerItem.hasResigned())
                    .toList();

            companyList.get(0).setOfficers(officer);
        }
        isActive(companyList.get(0), onlyActive);

        return isActive(companyList.get(0), onlyActive) ? companyList : new ArrayList<>();
    }

    private boolean isActive(Company company, boolean onlyActive){
        if(onlyActive && !company.getCompanyStatus().equalsIgnoreCase("active")){
            return false;
        }
        return true;
    }
}
