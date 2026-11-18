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
import com.freelance.portal.exception.ResourceNotFoundException;
import java.util.Objects;
import org.springframework.lang.NonNull;

/**
 * Service implementation for managing Job entities.
 * This class provides methods for job registration, retrieval, updating, and deletion.
 */
@Service
public class JobServiceImpl implements com.freelance.portal.services.JobService {

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
    public Optional<Job> getJobById(@NonNull Long id) {
        Objects.requireNonNull(id, "id must not be null");
        return jobRepository.findById(id);
    }

    @Override
    public Job updateJob(@NonNull Long id, Job updatedJob) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(updatedJob, "updatedJob must not be null");
        Optional<Job> existingJob = jobRepository.findById(id);
        if (existingJob.isPresent()) {
            Job job = existingJob.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setEmployer(updatedJob.getEmployer());
            return jobRepository.save(job);
        } else {
            throw new ResourceNotFoundException("Job not found with ID: " + id);
        }
    }

    @Override
    public String deleteJob(@NonNull Long id) {
        Objects.requireNonNull(id, "id must not be null");
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return "Job deleted successfully!";
        } else {
            throw new ResourceNotFoundException("Job not found with ID: " + id);
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
    public String applyForJob(@NonNull Long jobId, @NonNull Long userId) {
        Objects.requireNonNull(jobId, "jobId must not be null");
        Objects.requireNonNull(userId, "userId must not be null");
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
    public List<Job> getUserJobApplications(@NonNull Long userId) {
        Objects.requireNonNull(userId, "userId must not be null");
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return new ArrayList<>(userOpt.get().getAppliedJobs());
        }
        return new ArrayList<>();
    }
}
