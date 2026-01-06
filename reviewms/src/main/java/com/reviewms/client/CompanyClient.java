package com.reviewms.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANYMS",url = "${comapnyms.url}")
public interface CompanyClient {
    @GetMapping("/companies/{companyId}")
    ResponseEntity<JsonNode> getCompanySummary(@PathVariable Long companyId);

}
