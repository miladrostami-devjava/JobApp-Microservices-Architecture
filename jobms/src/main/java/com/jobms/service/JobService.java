package com.jobms.service;

import com.jobms.bean.Job;
import com.jobms.entity.JobEntity;
import com.jobms.response.JobResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobService {
    List<JobResponse> findAll();
    ResponseEntity<String> createJob(Job job);
    JobResponse getJobById(Long id);
    void deleteJobById(Long id);
    JobResponse updateJob(Long id, Job updatedjob);
     JobResponse toJobResponse(JobEntity jobEntity);
    boolean validateCompany(Long companyId);
    List<Job> findByCompanyId(Long companyId);
    void deleteByCompanyId(Long companyId);

}
