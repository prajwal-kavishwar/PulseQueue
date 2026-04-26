package com.prajwal.jobsystem.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
public class RedisQueueService {
    private final StringRedisTemplate redisTemplate;

    private static final String QUEUE_NAME="job_queue";
    public RedisQueueService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public void enqueue(String jobId) {
        redisTemplate.opsForList().rightPush(QUEUE_NAME, jobId);
    }

    public String dequeue() {
        return redisTemplate.opsForList().leftPop(QUEUE_NAME, Duration.ofSeconds(10));
    }

    public String dequeueBlocking() {
        return redisTemplate.opsForList()
                .leftPop(QUEUE_NAME, Duration.ofSeconds(10));
    }

    public Long getQueueSize() {
        return redisTemplate.opsForList().size(QUEUE_NAME);
    }

}
