package com.prajwal.jobsystem.service;


import com.prajwal.jobsystem.dto.JobRequest;
import com.prajwal.jobsystem.dto.JobResponse;
import com.prajwal.jobsystem.dto.JobStatsDTO;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;


    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);
    private final RedisQueueService queueService;
    public JobServiceImpl(JobRepository jobRepository, RedisQueueService queueService) {
        this.jobRepository = jobRepository;

        this.queueService = queueService;
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
        log.info("Processing job: {}", jobId);
        jobRepository.save(job);

        try{
            String result=executeJobLogic(job);
            job.setStatus(JobStatus.COMPLETED);
            job.setResult(result);
            job.setCompletedAt(LocalDateTime.now());

        }
        catch (Exception e){
            int retries = job.getRetryCount();
            if(retries<3) {
                job.setRetryCount(retries+1);
                job.setStatus(JobStatus.SUBMITTED); // retry again

                log.warn("Retrying job: {} attempt: {}", jobId, retries + 1);

                jobRepository.save(job);
                submitJob(jobId);//retry job(submit)
                return;
            }
            job.setStatus(JobStatus.FAILED);
            job.setError(e.getMessage());
            job.setCompletedAt(LocalDateTime.now());
        }
        jobRepository.save(job);
    }
    private String executeJobLogic(Job job)throws Exception{

        switch (job.getType().toUpperCase()){
            case "EMAIL":
                return "email sent successfully";
            case "REPORT":
                return "Report generated";

            default:
                throw new IllegalArgumentException("Unknown job type");
        }
    }
    public void submitJob(UUID jobId){
        log.info("Submitting job: {}" ,jobId);
        queueService.enqueue(jobId.toString());
    }


    public JobStatsDTO getJobStats() {

        long submitted = jobRepository.countByStatus(JobStatus.SUBMITTED);
        long processing = jobRepository.countByStatus(JobStatus.PROCESSING);
        long completed = jobRepository.countByStatus(JobStatus.COMPLETED);
        long failed = jobRepository.countByStatus(JobStatus.FAILED);

        return JobStatsDTO.builder()
                .submitted(submitted)
                .processing(processing)
                .completed(completed)
                .failed(failed)
                .build();
    }





}
































