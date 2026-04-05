package com.prajwal.jobsystem.repository;

import com.prajwal.jobsystem.model.JobStatus;
import com.prajwal.jobsystem.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.UUID;


@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {

    List<Job> findByStatus(JobStatus status);
}
