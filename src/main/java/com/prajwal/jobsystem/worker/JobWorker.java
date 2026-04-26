package com.prajwal.jobsystem.worker;


import com.prajwal.jobsystem.service.JobServiceImpl;
import com.prajwal.jobsystem.service.RedisQueueService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class JobWorker {

    private static final Logger log = LoggerFactory.getLogger(JobWorker.class);
    private final RedisQueueService queueService;
    private final JobServiceImpl jobService;

    private ExecutorService workerPool;

    @Value("${worker.thread.count}")
    private int workerCount;


    public JobWorker(RedisQueueService queueService, JobServiceImpl jobService) {
        this.queueService = queueService;
        this.jobService = jobService;
    }
    @PostConstruct
    public void startWorker(){
        workerPool = Executors.newFixedThreadPool(workerCount);
        for (int i = 0; i < workerCount; i++) {
            workerPool.submit(()->{
                while (true) {
                    try {
                        String jobId = queueService.dequeueBlocking();

                        if (jobId != null) {
                            log.info("Worker {} processing job {}",
                                    Thread.currentThread().getName(), jobId);
                            jobService.processJob(UUID.fromString(jobId));
                        }

                    } catch (Exception e) {
                        log.error("Worker error", e);
                    }
                }
            });
        }



    }

    @PreDestroy
    public void shutdown() {
        workerPool.shutdown();
    }
}
