package com.prajwal.jobsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest {
    @NotBlank(message = "Job type is required")
    private String type;
    @NotBlank(message = "Payload cannot be empty")
    private String payload;


}
