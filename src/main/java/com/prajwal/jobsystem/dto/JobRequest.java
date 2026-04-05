package com.prajwal.jobsystem.dto;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest {
    private String type;
    private String payload;


}
