package com.jobapp.companyms.service;

import com.jobapp.companyms.bean.JobSummary;

import java.util.List;

public interface JobClientService {

    List<JobSummary> getJobSummary(Long companyId);
    void deleteJobsByCompany(Long id);
    boolean isAvailable();
    List<JobSummary> getJobFallback(Long companyId,Throwable throwable);
    void deleteJobFallback(Long companyId,Throwable throwable);

}
