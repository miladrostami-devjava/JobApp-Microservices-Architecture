package com.reviewms.service;

import com.reviewms.bean.Review;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {

    List<Review> findAll(Long companyId);
    ResponseEntity<String> addReview(Long companyId, Review review);
    Review findById(Long reviewId);
    void updateReview(Long reviewId, Review review);
    void deleteReviewById(Long reviewId);
    void deleteByCompanyId(Long companyId);
    boolean validateCompany(Long companyId);

}
