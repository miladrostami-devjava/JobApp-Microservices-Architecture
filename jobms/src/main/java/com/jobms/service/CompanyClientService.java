package com.jobms.service;

import com.jobms.bean.CompanySummary;
import org.springframework.http.ResponseEntity;

public interface CompanyClientService {
    ResponseEntity<CompanySummary> getCompanySummary(Long companyId);
    ResponseEntity<CompanySummary> companyFallback(Long companyId,Throwable throwable);
}
