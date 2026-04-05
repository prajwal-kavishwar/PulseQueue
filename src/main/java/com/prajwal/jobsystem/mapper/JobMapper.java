package com.prajwal.jobsystem.mapper;

import com.prajwal.jobsystem.dto.JobResponse;
import com.prajwal.jobsystem.model.Job;

public class JobMapper {
    public static JobResponse jobResponse(Job job){
        JobResponse response=new JobResponse();

        response.setId(job.getId());
        response.setType(job.getType());
        response.setPayload(job.getPayload());
        response.setStatus(job.getStatus());
        response.setResult(job.getResult());
        response.setError(job.getError());
        response.setCreatedAt(job.getCreatedAt());
        response.setUpdatedAt(job.getUpdatedAt());
        response.setCompletedAt(job.getCompletedAt());

        return response;

    }

}
