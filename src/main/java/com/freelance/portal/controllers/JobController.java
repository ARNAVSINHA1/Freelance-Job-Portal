package com.freelance.portal.controllers;

import com.freelance.portal.models.Job;
import com.freelance.portal.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

	@Autowired
	private JobService jobService;

	@PostMapping("/register")
	public ResponseEntity<String> registerJob(@RequestBody Job job) {
		jobService.registerJob(job);
		return ResponseEntity.ok("Job registered successfully!");
	}

	@GetMapping("/list")
	public List<Job> listJobs() {
		return jobService.listJobs();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Job>> getJobById(@PathVariable Long id) {
		Optional<Job> job = jobService.getJobById(id);
		return job.isPresent() ? ResponseEntity.ok(job) : ResponseEntity.notFound().build();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
		Job updatedJob = jobService.updateJob(id, job);
		return ResponseEntity.ok(updatedJob);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteJob(@PathVariable Long id) {
		return ResponseEntity.ok(jobService.deleteJob(id));
	}

	@GetMapping("/employer/{employer}")
	public List<Job> findJobsByEmployer(@PathVariable String employer) {
		return jobService.findJobsByEmployer(employer);
	}

	@GetMapping("/search")
	public List<Job> searchJobsByTitle(@RequestParam String title) {
		return jobService.searchJobsByTitle(title);
	}

	@PostMapping("/apply/{jobId}/user/{userId}")
	public ResponseEntity<String> applyForJob(@PathVariable Long jobId, @PathVariable Long userId) {
		return ResponseEntity.ok(jobService.applyForJob(jobId, userId));
	}

	@GetMapping("/applications/{userId}")
	public List<Job> getUserJobApplications(@PathVariable Long userId) {
		return jobService.getUserJobApplications(userId);
	}

}
