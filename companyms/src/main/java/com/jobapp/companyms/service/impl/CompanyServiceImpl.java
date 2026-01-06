package com.jobapp.companyms.service.impl;

import com.jobapp.companyms.bean.Company;
import com.jobapp.companyms.bean.JobSummary;
import com.jobapp.companyms.bean.ReviewSummary;
import com.jobapp.companyms.dao.CompanyRepository;
import com.jobapp.companyms.entity.CompanyEntity;
import com.jobapp.companyms.eventDTO.ReviewCreatedEvent;
import com.jobapp.companyms.eventDTO.ReviewDeletedEvent;
import com.jobapp.companyms.eventDTO.ReviewUpdatedEvent;
import com.jobapp.companyms.exception.CompanyNotFoundException;
import com.jobapp.companyms.mapper.CompanyMapper;
import com.jobapp.companyms.messaging.CompanyEventPublisher;
import com.jobapp.companyms.response.CompanyResponse;
import com.jobapp.companyms.service.CompanyService;
import com.jobapp.companyms.service.JobClientService;
import com.jobapp.companyms.service.ReviewClientService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyEventPublisher companyEventPublisher;
    private final JobClientService jobClientService;
    private final ReviewClientService reviewClientService;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper, CompanyEventPublisher companyEventPublisher, @Lazy JobClientService jobClientService, ReviewClientService reviewClientService) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.companyEventPublisher = companyEventPublisher;
        this.jobClientService = jobClientService;
        this.reviewClientService = reviewClientService;
    }


    @Override
    public List<Company> findAll() {
        return companyMapper.toBeanList(companyRepository.findAll());
    }

    @Override
    public Company createCompany(Company company) {
        company.setId(null);
        CompanyEntity entity = companyMapper.toEntity(company);

        return companyMapper.toBean(companyRepository.save(entity));
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        CompanyEntity companyEntity = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company With ID :" + id + " not found"));
        return toCompanyResponse(companyEntity);
    }

    @Override
    public boolean updateCompany(Long id, Company updatedCompany) {
        Optional<CompanyEntity> companyEntity = companyRepository.findById(id);
        if (companyEntity.isPresent()) {
            CompanyEntity existingCompanyEntity = companyEntity.get();
            companyMapper.updateEntityFromBean(updatedCompany, existingCompanyEntity);
            companyRepository.save(existingCompanyEntity);
            return true;
        } else {
            throw new CompanyNotFoundException("Company With ID :" + id + "not found.");
        }
    }

    @Override
    public CompanyResponse toCompanyResponse(CompanyEntity entity) {
        Company company = companyMapper.toBean(entity);
        List<JobSummary> jobSummariesResponse = jobClientService.getJobSummary(company.getId());
        List<ReviewSummary> reviewSummariesResponse = reviewClientService.getReviewSummary(company.getId());
        return new CompanyResponse(company, jobSummariesResponse, reviewSummariesResponse);
    }

    @Override
    public void deleteCompanyById(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new CompanyNotFoundException("Company With ID :" + id + "not found.");
        } else {
            companyRepository.deleteById(id);
            companyEventPublisher.publishCompanyDeletedEvent(id);
        }
    }

    @Override
    @Transactional
    public void updateCompanyRatingOnCreate(ReviewCreatedEvent event) {
        companyRepository.updateRatingAtomically(event.getCompanyId(), event.getRating(), 1);
    }

    @Override
    @Transactional
    public void updateCompanyRatingOnDelete(ReviewDeletedEvent event) {
        companyRepository.updateRatingAtomically(event.getCompanyId(), -event.getRating(), -1);
    }

    @Override
    @Transactional
    public void updateCompanyRatingOnUpdate(ReviewUpdatedEvent event) {
        companyRepository.updateRatingAtomically(event.getCompanyId(), event.getNewRating() - event.getOldRating(), 0);
    }
}
