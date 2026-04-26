

# *Distributed Background Job Processing System*

## *Overview*

This project is a backend system for handling *background jobs asynchronously*.
Instead of processing tasks directly in the API (which can slow things down), jobs are pushed to a *Redis queue* and processed in the background by worker threads.



## *Working of the project*

Client → API → Redis Queue → Worker Pool → PostgreSQL

* API receives job requests
* Job is stored in the database
* Job ID is pushed to Redis
* Workers pick jobs and process them in parallel
* Status and results are updated in PostgreSQL



## *Features*

* Asynchronous job processing
* Redis-based queue system
* Worker pool for parallel execution
* Retry mechanism for failed jobs
* Job status tracking (*SUBMITTED → PROCESSING → COMPLETED / FAILED*)
* Logging and validation
* Stats API for monitoring system state



## *Tech Stack*

* *Java 17*
* *Spring Boot*
* *Redis*
* *PostgreSQL*
* *Spring Data JPA*

## *API Endpoints*

* *POST /api/jobs* → Create a job
* *GET /api/jobs/{id}* → Get job details
* *GET /api/jobs/{id}/status* → Get job status
* *GET /api/jobs/stats* → Get system stats



## *Running the project*

1. Start Redis
2. Start PostgreSQL (create `job_system` database)
3. Run the app:

```
mvn spring-boot:run
```

---

## *Example Request*

```
{
  "type": "EMAIL",
  "payload": "test job"
}
```


## *What this project demonstrates*

* Queue-based system design
* Asynchronous processing
* Worker-consumer architecture
* Basic scalability concepts using Redis

## *Future Improvements*

* Separate worker service
* Kafka-based messaging
* Job scheduling


## *Summary*

This project focuses on building a *real-world style backend system* that goes beyond basic CRUD by introducing queues, workers, and scalable processing.
