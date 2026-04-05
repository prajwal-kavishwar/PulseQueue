package com.prajwal.jobsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "jobs", indexes = {
        @Index(name = "idx_job_status", columnList = "status"),
        @Index(name = "idx_job_created_at", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;


    @Column(columnDefinition = "TEXT")
    private String result;


    @Column(columnDefinition = "TEXT")
    private String error;


    @Column(columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable =true)
    private LocalDateTime completedAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
