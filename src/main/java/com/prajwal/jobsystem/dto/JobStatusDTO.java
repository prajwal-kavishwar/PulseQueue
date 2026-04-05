package com.prajwal.jobsystem.dto;


import com.prajwal.jobsystem.model.JobStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobStatusDTO {

    private UUID id;
    private JobStatus status;



}
