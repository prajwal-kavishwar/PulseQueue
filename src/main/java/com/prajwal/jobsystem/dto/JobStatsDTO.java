package com.prajwal.jobsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JobStatsDTO {
    private long submitted;
    private long processing;
    private long completed;
    private long failed;
}