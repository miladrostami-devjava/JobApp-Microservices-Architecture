package com.jobapp.companyms.service;


import com.jobapp.companyms.bean.ReviewSummary;

import java.util.List;

public interface ReviewClientService {
List<ReviewSummary> getReviewSummary(Long companyId);
void deleteReviewByCompany(Long id);
boolean isAvailable();
List<ReviewSummary> getReviewFallback(Long companyId,Throwable throwable);
void deleteReviewFallback(Long companyId,Throwable throwable);
}
