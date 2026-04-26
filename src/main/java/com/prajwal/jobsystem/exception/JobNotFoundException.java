package com.prajwal.jobsystem.exception;

import java.util.UUID;

public class JobNotFoundException extends RuntimeException{
    public JobNotFoundException(UUID jobId){
        super("Job not found with jobId : "+jobId);
    }
    public JobNotFoundException(String msg){
        super(msg);
    }


}
