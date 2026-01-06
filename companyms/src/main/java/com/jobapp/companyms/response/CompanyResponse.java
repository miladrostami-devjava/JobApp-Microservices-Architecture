package com.jobapp.companyms.response;

import com.jobapp.companyms.bean.Company;
import com.jobapp.companyms.bean.JobSummary;
import com.jobapp.companyms.bean.ReviewSummary;

import java.util.List;

public class CompanyResponse {

    private Company company;
    private List<JobSummary> jobSummary;
    private List<ReviewSummary> reviewSummary;



    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<JobSummary> getJobSummary() {
        return jobSummary;
    }

    public void setJobSummary(List<JobSummary> jobSummary) {
        this.jobSummary = jobSummary;
    }

    public List<ReviewSummary> getReviewSummary() {
        return reviewSummary;
    }

    public void setReviewSummary(List<ReviewSummary> reviewSummary) {
        this.reviewSummary = reviewSummary;
    }

    public CompanyResponse(Company company, List<JobSummary> jobSummary, List<ReviewSummary> reviewSummary) {
        this.company = company;
        this.jobSummary = jobSummary;
        this.reviewSummary = reviewSummary;
    }

}
