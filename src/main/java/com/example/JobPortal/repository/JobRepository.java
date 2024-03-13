package com.example.JobPortal.repository;

import com.example.JobPortal.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM jobs as j WHERE j.admin_id = ?1 AND (j.description LIKE %?2% OR j.profile LIKE %?2% )")
    List<JobEntity> searchJobsForAdmin(Long adminId, String keyword);

    @Query(nativeQuery = true, value = "SELECT * FROM jobs as j WHERE (j.description LIKE %?1% OR j.profile LIKE %?1% )")
    List<JobEntity> searchJobsForUser(String keyword);

}
