package com.freelance.portal.controllers;

import com.freelance.portal.models.Job;
import com.freelance.portal.services.JobService;
import com.freelance.portal.dto.JobRequest;
import com.freelance.portal.dto.JobResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Controller for managing Job entities in the Freelance Job Portal application.
 * This class handles HTTP requests related to job operations.
 */
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    /**
     * Creates a new job.
     * @param job The job to be created.
     * @return ResponseEntity containing the created job.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest request) {
        Job job = new Job();
        job.setTitle(request.title());
        job.setDescription(request.description());
        job.setEmployer(request.employer());

        Job createdJob = jobService.registerJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(JobResponse.from(createdJob));
    }

    /**
     * Retrieves all jobs.
     * @return ResponseEntity containing a list of all jobs.
     */
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> jobs = jobService.listJobs().stream()
                .map(JobResponse::from)
                .toList();
        return ResponseEntity.ok(jobs);
    }

    /**
     * Retrieves a job by its ID.
     * @param id The ID of the job to retrieve.
     * @return ResponseEntity containing the job if found, or a 404 response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable @NonNull Long id) {
        return jobService.getJobById(id)
                .map(JobResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates a job by its ID.
     * @param id The ID of the job to update.
     * @param job The updated job information.
     * @return ResponseEntity containing the updated job.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobResponse> updateJob(@PathVariable @NonNull Long id, @Valid @RequestBody JobRequest request) {
        Job job = new Job();
        job.setTitle(request.title());
        job.setDescription(request.description());
        job.setEmployer(request.employer());

        Job updatedJob = jobService.updateJob(id, job);
        return ResponseEntity.ok(JobResponse.from(updatedJob));
    }

    /**
     * Deletes a job by its ID.
     * @param id The ID of the job to delete.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteJob(@PathVariable @NonNull Long id) {
        String response = jobService.deleteJob(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves jobs by employer's name.
     * @param employer The name of the employer.
     * @return ResponseEntity containing a list of jobs associated with the specified employer.
     */
    @GetMapping("/employer/{employer}")
    public ResponseEntity<List<JobResponse>> getJobsByEmployer(@PathVariable String employer) {
        List<JobResponse> jobs = jobService.findJobsByEmployer(employer).stream()
                .map(JobResponse::from)
                .toList();
        return ResponseEntity.ok(jobs);
    }

    /**
     * Searches for jobs by title.
     * @param title The title to search for.
     * @return ResponseEntity containing a list of jobs that match the search criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<List<JobResponse>> searchJobs(@RequestParam String title) {
        List<JobResponse> jobs = jobService.searchJobsByTitle(title).stream()
                .map(JobResponse::from)
                .toList();
        return ResponseEntity.ok(jobs);
    }
}
