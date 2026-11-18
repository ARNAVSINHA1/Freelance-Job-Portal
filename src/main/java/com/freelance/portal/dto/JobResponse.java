package com.freelance.portal.dto;

import com.freelance.portal.models.Job;

public record JobResponse(Long id, String title, String description, String employer) {
    public static JobResponse from(Job job) {
        return new JobResponse(job.getId(), job.getTitle(), job.getDescription(), job.getEmployer());
    }
}
