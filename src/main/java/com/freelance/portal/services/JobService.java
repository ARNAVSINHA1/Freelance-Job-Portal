package com.freelance.portal.services;

import java.util.List;
import java.util.Optional;

import com.freelance.portal.models.Job;

public interface JobService {
	Job registerJob(Job job);

	List<Job> listJobs();

	Optional<Job> getJobById(Long id);

	Job updateJob(Long id, Job job);

	String deleteJob(Long id);

	List<Job> findJobsByEmployer(String employer);

	List<Job> searchJobsByTitle(String title);

	String applyForJob(Long jobId, Long userId);

	List<Job> getUserJobApplications(Long userId);
}
