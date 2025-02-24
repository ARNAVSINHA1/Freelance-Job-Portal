package com.freelance.portal.services;

import com.freelance.portal.models.Job;
import com.freelance.portal.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

	@Autowired
	private JobRepository jobRepository;

	public Job registerJob(Job job) {
		if (job.getTitle() == null || job.getTitle().isEmpty()) {
			throw new IllegalArgumentException("Job title cannot be empty");
		}
		return jobRepository.save(job);
	}

	public List<Job> listJobs() {
		return jobRepository.findAll();
	}

	public Optional<Job> getJobById(Long id) {
		return jobRepository.findById(id);
	}
}
