package com.jobapp.companyms.client;

import com.jobapp.companyms.bean.ReviewSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEWMS",url = "${reviewms.url}")
public interface ReviewClient {

    @GetMapping("/reviewms")
    List<ReviewSummary> getReviewsByCompany(@RequestParam(value = "companyId" ,required = true) Long companyId);

    @DeleteMapping("/reviewms")
    void deleteReviewByCompany(@RequestParam(value = "companyId",required = true) Long companyId);

    @GetMapping("/actuator/health")
    String healthCheck();

}
