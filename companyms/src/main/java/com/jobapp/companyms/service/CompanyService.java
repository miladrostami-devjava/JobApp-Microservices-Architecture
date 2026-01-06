package com.jobapp.companyms.service;

import com.jobapp.companyms.bean.Company;
import com.jobapp.companyms.entity.CompanyEntity;
import com.jobapp.companyms.eventDTO.ReviewCreatedEvent;
import com.jobapp.companyms.eventDTO.ReviewDeletedEvent;
import com.jobapp.companyms.eventDTO.ReviewUpdatedEvent;
import com.jobapp.companyms.response.CompanyResponse;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();
    Company createCompany(Company company);
    CompanyResponse getCompanyById(Long id);
    boolean updateCompany(Long id,Company updatedCompany);
    CompanyResponse toCompanyResponse(CompanyEntity entity);
    void deleteCompanyById(Long id);
    void updateCompanyRatingOnCreate(ReviewCreatedEvent event);
    void updateCompanyRatingOnDelete(ReviewDeletedEvent event);
    void updateCompanyRatingOnUpdate(ReviewUpdatedEvent event);

}
