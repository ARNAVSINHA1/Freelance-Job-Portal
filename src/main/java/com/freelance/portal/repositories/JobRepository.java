package com.freelance.portal.repositories;

import com.freelance.portal.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findByEmployer(String employer);

	List<Job> findByTitleContainingIgnoreCase(String title);
}
