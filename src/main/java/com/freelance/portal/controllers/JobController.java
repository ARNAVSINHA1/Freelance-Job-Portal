package com.freelance.portal.controllers;

import com.freelance.portal.models.Job;
import com.freelance.portal.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job createdJob = jobService.registerJob(job);
        return ResponseEntity.ok(createdJob);
    }

    /**
     * Retrieves all jobs.
     * @return ResponseEntity containing a list of all jobs.
     */
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.listJobs();
        return ResponseEntity.ok(jobs);
    }

    /**
     * Retrieves a job by its ID.
     * @param id The ID of the job to retrieve.
     * @return ResponseEntity containing the job if found, or a 404 response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return jobService.getJobById(id)
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
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
        Job updatedJob = jobService.updateJob(id, job);
        return ResponseEntity.ok(updatedJob);
    }

    /**
     * Deletes a job by its ID.
     * @param id The ID of the job to delete.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        String response = jobService.deleteJob(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves jobs by employer's name.
     * @param employer The name of the employer.
     * @return ResponseEntity containing a list of jobs associated with the specified employer.
     */
    @GetMapping("/employer/{employer}")
    public ResponseEntity<List<Job>> getJobsByEmployer(@PathVariable String employer) {
        List<Job> jobs = jobService.findJobsByEmployer(employer);
        return ResponseEntity.ok(jobs);
    }

    /**
     * Searches for jobs by title.
     * @param title The title to search for.
     * @return ResponseEntity containing a list of jobs that match the search criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(@RequestParam String title) {
        List<Job> jobs = jobService.searchJobsByTitle(title);
        return ResponseEntity.ok(jobs);
    }
}
