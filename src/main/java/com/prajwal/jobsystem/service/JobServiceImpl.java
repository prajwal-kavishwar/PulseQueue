package com.prajwal.jobsystem.service;


import com.prajwal.jobsystem.dto.JobRequest;
import com.prajwal.jobsystem.dto.JobResponse;
import com.prajwal.jobsystem.dto.JobStatusDTO;
import com.prajwal.jobsystem.exception.JobNotFoundException;
import com.prajwal.jobsystem.model.JobStatus;
import com.prajwal.jobsystem.mapper.JobMapper;

import com.prajwal.jobsystem.model.Job;
import com.prajwal.jobsystem.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;

    private final ExecutorService executorService;

    public JobServiceImpl(JobRepository jobRepository, ExecutorService executorService) {
        this.jobRepository = jobRepository;
        this.executorService = executorService;
    }


    @Override
    public JobResponse createJob(JobRequest request) {
        Job job=new Job();
        job.setType(request.getType());
        job.setPayload(request.getPayload());
        job.setStatus(JobStatus.SUBMITTED);
        Job savedJob=jobRepository.save(job);

        return JobMapper.jobResponse(savedJob);
    }

    @Override
    public JobResponse getJob(UUID jobId) {

        Job job=jobRepository.findById(jobId)
                .orElseThrow(()-> new JobNotFoundException(jobId));

        return JobMapper.jobResponse(job);
    }
    @Override
    public JobStatusDTO getJobStatus(UUID jobId){

        Job job=jobRepository.findById(jobId)
                .orElseThrow(()-> new JobNotFoundException(jobId));
        return new JobStatusDTO(jobId,job.getStatus());

    }

    @Transactional
    public void processJob(UUID jobId){

        Job job=jobRepository.findById(jobId)
                .orElseThrow(()-> new JobNotFoundException(jobId));

        if (job.getStatus() != JobStatus.SUBMITTED) {
            return;
        }
        job.setStatus(JobStatus.PROCESSING);
        System.out.println("Processing job: " + jobId);
        jobRepository.save(job);

        try{
            String result=executeJobLogic(job);
            job.setStatus(JobStatus.COMPLETED);
            job.setResult(result);
            job.setCompletedAt(LocalDateTime.now());

        }
        catch (Exception e){
            job.setStatus(JobStatus.FAILED);
            job.setResult(e.getMessage());
            job.setCompletedAt(LocalDateTime.now());
        }
        jobRepository.save(job);
    }
    private String executeJobLogic(Job job)throws Exception{

        switch (job.getType()){
            case "Email":
                return "email sent successfully";
            case "REPORT":
                return "Report generated";

            default:
                throw new IllegalArgumentException("Unknown job type");
        }
    }
    public void submitJob(UUID jobId){
        executorService.submit(()->processJob(jobId));
    }





}
































