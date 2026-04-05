package com.prajwal.jobsystem.controller;


import com.prajwal.jobsystem.dto.JobRequest;
import com.prajwal.jobsystem.dto.JobResponse;
import com.prajwal.jobsystem.dto.JobStatusDTO;
import com.prajwal.jobsystem.service.JobServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobServiceImpl service;

    public JobController(JobServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@RequestBody JobRequest request){
        JobResponse response=service.createJob(request);
        service.submitJob(response.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJob(@PathVariable UUID jobId){
        return ResponseEntity.ok(service.getJob(jobId));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<JobStatusDTO> getStatus(@PathVariable UUID jobId){
        return ResponseEntity.ok(service.getJobStatus(jobId));
    }



}
