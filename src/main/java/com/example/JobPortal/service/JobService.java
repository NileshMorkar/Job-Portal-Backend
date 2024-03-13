package com.example.JobPortal.service;

import com.example.JobPortal.model.response.JobResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {


    List<JobResponse> getAllJobs();

    List<JobResponse> searchJobsForUser(String keyword);
}
