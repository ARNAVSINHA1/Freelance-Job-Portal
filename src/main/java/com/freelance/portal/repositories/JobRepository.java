package com.freelance.portal.repositories;

import com.freelance.portal.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing Job entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    /**
     * Finds jobs by the employer's name.
     * @param employer The name of the employer.
     * @return A list of jobs associated with the specified employer.
     */
    List<Job> findByEmployer(String employer);


    /**
     * Searches for jobs by title, ignoring case.
     * @param title The title to search for.
     * @return A list of jobs that contain the specified title.
     */
    List<Job> findByTitleContainingIgnoreCase(String title);

}
