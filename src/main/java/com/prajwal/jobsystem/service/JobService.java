package com.prajwal.jobsystem.service;

import com.prajwal.jobsystem.dto.JobRequest;
import com.prajwal.jobsystem.dto.JobResponse;
import com.prajwal.jobsystem.dto.JobStatusDTO;

import java.util.UUID;

public interface JobService {
    JobResponse createJob(JobRequest request);

    JobResponse getJob(UUID jobId);

    JobStatusDTO getJobStatus(UUID jobId);

}
