package com.jobapp.companyms.client;


import com.jobapp.companyms.bean.JobSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "JOBMS",url = "${jobms.url}")
public interface JobClient {

    @GetMapping("/jobs")
    List<JobSummary> getJobsByCompany(@RequestParam(value = "companyId",required = true) Long companyId);

    @DeleteMapping("/jobs")
    void deleteJobByCompany(@RequestParam(value = "companyId",required = true) Long companyId);

    @GetMapping("/actuator/health")
    String healthCheck();


}
