package com.jobapp.companyms.controller;

import com.jobapp.companyms.bean.Company;
import com.jobapp.companyms.response.CompanyResponse;
import com.jobapp.companyms.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Company> createCompany(Company company){
        return ResponseEntity.ok(companyService.createCompany(company));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompanyById(@PathVariable Long id,@RequestBody Company updatedCompany){
        companyService.updateCompany(id,updatedCompany);
        return ResponseEntity.ok("Company updated successfully!");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
        companyService.deleteCompanyById(id);
        return ResponseEntity.ok("Company deleted successfully.");
    }
}
