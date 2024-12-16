package com.angMetal.orders.service;

import com.angMetal.orders.entity.Company;
import com.angMetal.orders.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public Company updateCompany(Long companyId, Company company) {
        // Fetch the existing company from the repository
        Company existingCompany = getCompanyById(companyId);

        // Update the fields of the existing company with the new values
        existingCompany.setDescription(company.getDescription());
        existingCompany.setName(company.getName());
        existingCompany.setLocation(company.getLocation());

        // Save the updated company back to the repository
        return companyRepository.save(existingCompany); // Save and return the updated company
    }

    public void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }
}
