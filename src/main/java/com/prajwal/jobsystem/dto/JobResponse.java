package com.prajwal.jobsystem.dto;

import com.prajwal.jobsystem.model.JobStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponse {

    private UUID id;

    private String type;
    private String payload;
    private JobStatus status;

    private String result;
    private String error;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;



}
