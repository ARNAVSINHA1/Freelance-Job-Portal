package com.freelance.portal.services;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

import com.freelance.portal.models.Job;

public interface JobService {
	Job registerJob(Job job);

	List<Job> listJobs();

	Optional<Job> getJobById(@NonNull Long id);

	Job updateJob(@NonNull Long id, Job job);

	String deleteJob(@NonNull Long id);

	List<Job> findJobsByEmployer(String employer);

	List<Job> searchJobsByTitle(String title);

	String applyForJob(@NonNull Long jobId, @NonNull Long userId);

	List<Job> getUserJobApplications(@NonNull Long userId);
}
