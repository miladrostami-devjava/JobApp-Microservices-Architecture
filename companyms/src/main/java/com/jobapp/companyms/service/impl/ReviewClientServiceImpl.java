package com.jobapp.companyms.service.impl;

import com.jobapp.companyms.bean.ReviewSummary;
import com.jobapp.companyms.client.ReviewClient;
import com.jobapp.companyms.service.ReviewClientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewClientServiceImpl implements ReviewClientService {
    private final ReviewClient reviewClient;
    private static final Logger logger = LoggerFactory.getLogger(ReviewClientServiceImpl.class);

    public ReviewClientServiceImpl(ReviewClient reviewClient) {
        this.reviewClient = reviewClient;
    }

    @Override
    @RateLimiter(name = "reviewBreaker")
    @Retry(name = "reviewBreaker")
    @CircuitBreaker(name = "reviewBreaker",fallbackMethod = "getReviewFallback")
    public List<ReviewSummary> getReviewSummary(Long companyId) {
        return reviewClient.getReviewsByCompany(companyId);
    }

    @Override
    @RateLimiter(name = "reviewBreaker")
    @Retry(name = "reviewBreaker")
    @CircuitBreaker(name = "reviewBreaker",fallbackMethod = "deleteReviewFallback")
    public void deleteReviewByCompany(Long id) {
reviewClient.deleteReviewByCompany(id);
    }

    @Override
    public boolean isAvailable() {
        try {
            String response = reviewClient.healthCheck();
            return response != null && response.contains("\"status\":\"UP\"");
        }catch (Exception e){
            //FeignException ,Timeout,Connect refused,etc.
            return false;
        }
    }

    @Override
    public List<ReviewSummary> getReviewFallback(Long companyId, Throwable throwable) {
        logger.error("Review service is unavailable. Falling back to empty review list for companyId: {}", companyId, throwable);
        return List.of();
    }

    @Override
    public void deleteReviewFallback(Long companyId, Throwable throwable) {
        logger.error("Review service is Unavailable. Unable to delete reviews for companyId : {}.",companyId,throwable);
        throw new RuntimeException("Review service is currently unavailable. Please try again later.");

    }
}
