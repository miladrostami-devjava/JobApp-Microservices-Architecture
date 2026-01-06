package com.jobms.dao;

import com.jobms.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity,Long> {
    List<JobEntity> findByCompanyId(Long companyId);
}

