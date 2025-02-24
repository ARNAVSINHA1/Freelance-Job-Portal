package com.freelance.portal.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.portal.models.Job;
import com.freelance.portal.services.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

	@Autowired
	private JobService jobService;

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("Test endpoint works!");
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerJob(@RequestBody Job job) {
		try {
			Job savedJob = jobService.registerJob(job);
			return ResponseEntity.ok(savedJob);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list")
	public List<Job> listJobs() {
		return jobService.listJobs();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getJobById(@PathVariable Long id) {
		Optional<Job> job = jobService.getJobById(id);
		return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
