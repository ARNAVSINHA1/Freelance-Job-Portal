package com.freelance.portal.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freelance.portal.models.Job;
import com.freelance.portal.models.User;
import com.freelance.portal.repositories.JobRepository;
import com.freelance.portal.repositories.UserRepository;

@Service
public class JobService implements com.freelance.portal.services.JobService {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Job registerJob(Job job) {
		if (job.getTitle() == null || job.getTitle().isEmpty()) {
			throw new IllegalArgumentException("Job title cannot be empty");
		}
		return jobRepository.save(job);
	}

	@Override
	public List<Job> listJobs() {
		return jobRepository.findAll();
	}

	@Override
	public Optional<Job> getJobById(Long id) {
		return jobRepository.findById(id);
	}

	@Override
	public Job updateJob(Long id, Job updatedJob) {
		Optional<Job> existingJob = jobRepository.findById(id);
		if (existingJob.isPresent()) {
			Job job = existingJob.get();
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setEmployer(updatedJob.getEmployer());
			return jobRepository.save(job);
		} else {
			throw new IllegalArgumentException("Job not found with ID: " + id);
		}
	}

	@Override
	public String deleteJob(Long id) {
		if (jobRepository.existsById(id)) {
			jobRepository.deleteById(id);
			return "Job deleted successfully!";
		} else {
			return "Job not found with ID: " + id;
		}
	}

	@Override
	public List<Job> findJobsByEmployer(String employer) {
		return jobRepository.findByEmployer(employer);
	}

	@Override
	public List<Job> searchJobsByTitle(String title) {
		return jobRepository.findByTitleContainingIgnoreCase(title);
	}

	@Override
	public String applyForJob(Long jobId, Long userId) {
		Optional<Job> jobOpt = jobRepository.findById(jobId);
		Optional<User> userOpt = userRepository.findById(userId);

		if (jobOpt.isPresent() && userOpt.isPresent()) {
			User user = userOpt.get();
			Job job = jobOpt.get();

			user.applyForJob(job);
			userRepository.save(user);

			return "Application successful!";
		}
		return "Job or User not found!";
	}

	@Override
	public List<Job> getUserJobApplications(Long userId) {
		Optional<User> userOpt = userRepository.findById(userId);
		return userOpt.map(User::getAppliedJobs).map(ArrayList::new).orElseGet(ArrayList::new);
	}

}
